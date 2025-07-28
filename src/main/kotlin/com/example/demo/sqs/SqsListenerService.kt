package com.example.demo.sqs

import io.awspring.cloud.sqs.annotation.SqsListener
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SqsListenerService(
    private val excelProcessor: ExcelProcessor,
    private val sqsTemplate: SqsTemplate,
    @Value("\${app.sqs.dlq-name}") private val dlqName: String
) {

    private val logger = LoggerFactory.getLogger(SqsListenerService::class.java)

    @SqsListener("\${app.sqs.queue-name}")
    fun handleMessage(message: String) {
        try {
            excelProcessor.process(message)
        } catch (ex: Exception) {
            logger.error("Error processing message", ex)
            sqsTemplate.sendAsync(dlqName, message)
        }
    }
}
