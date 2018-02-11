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

 public class RepositoryStory {

    public static MongoClient mongo;
    public static DB db;

    public RepositoryStory(MongoClient mongo, DB, db) {
        this.mongo = mongo;
        this.db = db;
    }

    public synchronized boolean createStory(Story story) {
        // TODO finish
        return false;
    }

    public synchronized Story readStory(int storyUID) {
        // TODO
        return null;
    }

    public synchronized boolean updateStory(int storyUID, Story story) {
        // TODO
        return false;
    }

    public synchronized boolean deleteStory(int storyUID) {
        return false;
    }

 }
