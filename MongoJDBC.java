package com.example;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class MongoJDBC {
    // Configuration constants
    private static final String MONGO_URI = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "testdb";
    private static final String COLLECTION_NAME = "users";

    public static void main(String[] args) {
        MongoJDBC app = new MongoJDBC();
        app.run();
    }

    private void run() {
        try (MongoClient mongoClient = createMongoClient()) {
            if (!isConnected(mongoClient)) {
                System.err.println("Failed to connect to MongoDB");
                return;
            }

            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            insertDocuments(collection);
            findAndPrintDocuments(collection);

        } catch (MongoException e) {
            System.err.println("MongoDB Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    private MongoClient createMongoClient() {
        return MongoClients.create(MONGO_URI);
    }

    private boolean isConnected(MongoClient client) {
        try {
            // Try to execute a simple operation to verify the connection
            client.listDatabaseNames().first();
            return true;
        } catch (MongoException e) {
            return false;
        }
    }

    private void insertDocuments(MongoCollection<Document> collection) throws MongoException {
        collection.insertOne(new Document("name", "John")
                               .append("age", 30));
        collection.insertOne(new Document("name", "Alice")
                               .append("age", 25));
        System.out.println("Successfully inserted documents");
    }

    private void findAndPrintDocuments(MongoCollection<Document> collection) {
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document user = cursor.next();
                System.out.println("ID: " + user.getObjectId("_id")
                               + ", Name: " + user.getString("name")
                               + ", Age: " + user.getInteger("age"));
            }
        }
    }
}
