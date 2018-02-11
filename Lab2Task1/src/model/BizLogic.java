import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

/**
    @author Dillon Dickerson
    @version 2/8/18
 **/

public class BizLogic {

	public static MongoClient mongo;
	public static DB db;
	public static RepositoryUser userDB;
	public static RepositoryStory storyDB;

 	public void init() {
		System.out.println("initializing BizLogic");
		this.mongo = new MongoClient("localhost", 27017);
		this.db = this.mongo.getDB("NNSDB");						// new story site DB
		this.userDB = new RepositoryUser(this.mongo, this.db);
		this.storyDB = new RepositoryStory(this.mongo, this.db);
	}

	public void destroy() {
		System.out.println("clossing DB connection");
		this.mongo.close();
		System.out.println("closing BizLogic");
	}


    /**
        TODO implement
        NOTE This will interact with database
    **/
    public static boolean login(String username, String password) {
		if (username.equals("username") && password.equals("password")) {
			return true;
		} else {
			return false;
		}
    }

	public static boolean userExists(String username) {
		if (null != userDB.readUser(username)) {
			return true;
		} else {
			return false;
		}
	}

    /*
        NOTE All usernames should be unique
        NOTE Roles: guest, Subcriber, Reporter
    */
    public static String getRole(String username) {
        return "guest";
    }
}
