package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import io.awspring.cloud.sqs.annotation.EnableSqs

@SpringBootApplication
@EnableSqs
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
