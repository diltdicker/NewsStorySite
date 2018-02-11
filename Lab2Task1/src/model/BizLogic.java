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

	/**
		NOTE Only Run Once
	**/
	public static void InitDB() {
		this.mongo = new MongoClient("localhost", 27017);
		this.db = mongo.getDB("testDB");
		this.userDB = new RepositoryUser(this.mongo, this.db);
		this.storyDB = new RepositoryStory(this.mongo, this.db);
	}

    /**
        TODO implement
        NOTE This will interact with database
    **/
    public static boolean login(String username, String password) {
        return false;
    }

    /*
        NOTE All usernames should be unique
        NOTE Roles: guest, Subcriber, Reporter
    */
    public static String getRole(String username) {
        return "guest";
    }
}
