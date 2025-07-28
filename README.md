# kotlin-app-back-end

Example Spring Boot application written in Kotlin that listens to an AWS SQS
queue. When a message is received the path to an Excel file is processed using
Apache POI. If an error occurs the original message is sent to a dead letter
queue (DLQ). Messages in the DLQ are automatically reprocessed by a scheduled
task in the same application.

Queue names can be configured in `src/main/resources/application.properties`:

```
app.sqs.queue-name=yourQueue
app.sqs.dlq-name=yourDlq
```

The listener is implemented in `SqsListenerService` and depends on
`ExcelProcessor` for the file handling logic. The DLQ messages are handled by
`DlqReprocessorService`, which polls the DLQ periodically. The polling interval
can be configured using `app.dlq.reprocess-interval` (in milliseconds).
