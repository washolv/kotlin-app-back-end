package com.example.demo.sqs

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File

@Service
class ExcelProcessor {
    private val logger = LoggerFactory.getLogger(ExcelProcessor::class.java)

    fun process(filePath: String) {
        val file = File(filePath)
        logger.info("Processing Excel file {}", file.absolutePath)
        XSSFWorkbook(file.inputStream()).use { workbook ->
            val sheet = workbook.getSheetAt(0)
            val cellValue = sheet.getRow(0)?.getCell(0)?.toString()
            logger.info("First cell value: {}", cellValue)
        }
    }
}
