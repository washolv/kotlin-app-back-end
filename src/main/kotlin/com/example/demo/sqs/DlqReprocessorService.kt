package com.example.demo.sqs

import io.awspring.cloud.sqs.operations.SqsTemplate
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class DlqReprocessorService(
    private val excelProcessor: ExcelProcessor,
    private val sqsTemplate: SqsTemplate,
    @Value("\${app.sqs.dlq-name}") private val dlqName: String
) {

    private val logger = LoggerFactory.getLogger(DlqReprocessorService::class.java)

    @Scheduled(fixedDelayString = "\${app.dlq.reprocess-interval:60000}")
    fun reprocess() {
        val messageOpt = sqsTemplate.receive { options ->
            options.queue(dlqName).maxNumberOfMessages(1)
        }

        messageOpt.ifPresent { message ->
            val payload = message.payload as String
            try {
                excelProcessor.process(payload)
                Acknowledgement.acknowledge(message)
            } catch (ex: Exception) {
                logger.error("Error reprocessing message from DLQ", ex)
            }
        }
    }
}
