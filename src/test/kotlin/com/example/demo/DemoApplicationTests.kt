package com.example.demo

import com.example.demo.sqs.ExcelProcessor
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    lateinit var excelProcessor: ExcelProcessor

    @Test
    fun contextLoads() {
        assertNotNull(excelProcessor)
    }
}
