package com.sarah.anotaai.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqsConsumer implements RequestHandler<SQSEvent, Void> {

    private static final Logger log = LoggerFactory.getLogger(SqsConsumer.class);

    private static final String MONGO_URI = System.getenv("MONGO_DB_HOST");
    private static final String DATABASE_NAME = "anotaai";
    private static final String COLLECTION_NAME = "catalog";


    private static final MongoClient mongoClient = MongoClients.create(MONGO_URI);
    private static final MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
    private static final MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        log.info("Mensagens recebidas do SQS: {}", event.getRecords().size());

        for (SQSEvent.SQSMessage message : event.getRecords()) {
            try {
                String messageBody = message.getBody();
                log.info("Processando mensagem: {}", messageBody);

                Document document = new Document("message", messageBody)
                        .append("processedAt", System.currentTimeMillis());

                collection.insertOne(document);
                log.info("Mensagem salva no MongoDB!");

            } catch (Exception e) {
                log.error("Erro ao processar mensagem do SQS", e);
            }
        }
        return null;
    }
}
