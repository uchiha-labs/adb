import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.FindIterable;

public class MongoDBConnect {
    public static void main(String[] args) {
        
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        MongoDatabase database = mongoClient.getDatabase("school");

        MongoCollection<Document> collection = database.getCollection("students");

        FindIterable<Document> docs = collection.find();
        for (Document doc : docs) {
            System.out.println(doc.toJson());
        }

        mongoClient.close();
    }
}
