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
		System.out.println("initializing BizLogic init");
	}

	public void destroy() {
		System.out.println("clossing DB connection");
		this.mongo.close();
		System.out.println("closing BizLogic");
	}

	public BizLogic() {
		System.out.println("initializing BizLogic");
		this.mongo = new MongoClient("localhost", 27017);
		this.db = this.mongo.getDB("NNSDB");						// new story site DB
		this.userDB = new RepositoryUser(this.mongo, this.db);
		this.storyDB = new RepositoryStory(this.mongo, this.db);

		userDB.createUser(new User("admin", "Administrator", "password", UserRoles.Admin));
	}


    /**
        TODO implement
        NOTE This will interact with database
    **/
    public static boolean login(String username, String password) {
		password = PassHash.hash(password);
		String usr = "";
		String pass = "";
		User user = userDB.readUser(username);
		if (user != null) {
			System.out.println("user logged in :" + username);
			usr = user.getUserName();
			pass = user.getPassHash();
		}
		if (username.equals(usr) && password.equals(pass)) {
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
    public static UserRoles getRole(String username) {
		User user = userDB.readUser(username);
		if (user != null) {
			System.out.println("not guest");
			return user.getRole();
		} else {
			System.out.println("guest");
			return UserRoles.Guest;
		}
    }

	/*public static User getUser(String username) {
		User user = userDB.readUser(username);
		return user;
	}*/
}
