import com.mongodb.client.*;
import org.bson.*;
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
/**output
{"_id": 1, "name": "John",  "age": 21}
{"_id": 2, "name": "Alice", "age": 22}
{"_id": 3, "name": "Bob",   "age": 23}
**/
