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
    private DBCollection userTable;

    public RepositoryUser(MongoClient mongo, DB db) {
        this.mongo = mongo;
        this.db = db;
        this.userTable = db.getCollection("Users");
    }

    public synchronized boolean createUser(User user) {
        boolean status = true;
        try {
            BasicDBObject userDoc = new BasicDBObject();
			userDoc.put("username", user.getUserName());
			userDoc.put("passHash", user.getPassHash());
			userDoc.put("name", user.getName());
            userDoc.put("role", user.getRole().toString());
            this.userTable.insert(userDoc);
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public synchronized User readUser(String username) {
        System.out.println("READ USER");
        BasicDBObject query = new BasicDBObject();
        User result = null;
        try {
            query.put("username", username);
            result = new User(userTable.findOne(query).toString());
            System.out.println("Sucsses:");
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        } finally {
            return result;
        }
    }

    public synchronized boolean updateUser(String username, User user) {
        boolean status = true;
        try {
            BasicDBObject userDoc = new BasicDBObject();
            BasicDBObject oldUserDoc = new BasicDBObject();
            BasicDBObject update = new BasicDBObject();
            oldUserDoc.put("username", username);
			userDoc.put("username", user.getUserName());
			userDoc.put("passHash", user.getPassHash());
			userDoc.put("name", user.getName());
            userDoc.put("role", user.getRole().toString());
            update.put("$set", userDoc);
            this.userTable.update(oldUserDoc, update);
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public synchronized boolean deleteUser(String username) {
        boolean status = true;
        try {
            BasicDBObject query = new BasicDBObject();
            query.put("username", username);
            System.out.println(userTable.remove(query));
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        } finally {
            return status;
        }
    }
 }
