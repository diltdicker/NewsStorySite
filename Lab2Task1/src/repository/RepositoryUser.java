import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

/**
    @author Dillon Dickerson
    @version 2/10/18
 **/

 public class RepositoryUser {

    public static MongoClient mongo;
    public static DB db;

    public RepositoryUser(MongoClient mongo, DB, db) {
        this.mongo = mongo;
        this.db = db;
    }

    public synchronized boolean createUser(User user) {
        // TODO
        return false;
    }

    public synchronized User readUser(String username) {
        // TODO
        return null;
    }

    public synchronized boolean updateUser(String username, User user) {
        // TODO
        return false;
    }

    public synchronized boolean deleteUser(String username) {
        return false;
    }
 }
