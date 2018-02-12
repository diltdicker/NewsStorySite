import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import java.util.ArrayList;

/**
    @author Dillon Dickerson
    @version 2/10/18
 **/

 public class RepositoryStory {

    public static MongoClient mongo;
    public static DB db;
    private static int lastPostID;
    private static DBCollection storyTable;

    public RepositoryStory(MongoClient mongo, DB db) {
        this.mongo = mongo;
        this.db = db;
        this.storyTable = db.getCollection("Stories");

    }

    public synchronized boolean createStory(Story story) {
        boolean status = true;
        try {
            BasicDBObject storyDoc = new BasicDBObject();
			storyDoc.put("postID", ++lastPostID);
            storyDoc.put("author", story.getAuthor());
            storyDoc.put("title", story.getTitle());
            storyDoc.put("content", story.getContent());
            storyDoc.put("subscriberOnly", story.getSubscriberOnly());
            storyDoc.put("subscriberList", story.getSubscriberList());
            this.storyTable.insert(storyDoc);
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public synchronized Story readStory(int storyUID) {
        System.out.println("READ STORY");
        BasicDBObject query = new BasicDBObject();
        Story result = null;
        try {
            query.put("postID", storyUID);
            result = new Story(storyTable.findOne(query).toString());
            System.out.println("Sucsses:");
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        } finally {
            return result;
        }
    }

    public synchronized boolean updateStory(int storyUID, Story story) {
        boolean status = true;
        try {
            BasicDBObject storyDoc = new BasicDBObject();
            BasicDBObject oldStoryDoc = new BasicDBObject();
            BasicDBObject update = new BasicDBObject();
            oldStoryDoc.put("postID", storyUID);
            storyDoc.put("author", story.getAuthor());
            storyDoc.put("title", story.getTitle());
            storyDoc.put("content", story.getContent());
            storyDoc.put("subscriberOnly", story.getSubscriberOnly());
            storyDoc.put("subscriberList", story.getSubscriberList());
            update.put("$set", storyDoc);
            this.storyTable.update(oldStoryDoc, update);
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public synchronized boolean deleteStory(int storyUID) {
        boolean status = true;
        try {
            BasicDBObject query = new BasicDBObject();
            query.put("postID", storyUID);
            System.out.println(storyTable.remove(query));
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        } finally {
            return status;
        }
    }



 }
