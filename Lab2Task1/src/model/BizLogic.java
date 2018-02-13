import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import java.util.ArrayList;

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


		ArrayList<Story> allStories = this.storyDB.getAllStories(false);
		for (int i = 0; i < allStories.size(); i++) {
			if (allStories.get(i).getPostID() > this.storyDB.lastPostID) {
				this.storyDB.lastPostID = allStories.get(i).getPostID();
			}
		}
		System.out.println("LAST POST ID: " + this.storyDB.lastPostID);
		//userDB.createUser(new User("admin", "Administrator", "11297115115119111114100", UserRoles.Admin));		//password = password
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

			System.out.println("input: " + password);
			System.out.println("stored: " + pass);
		}
		if (username.equals(usr) && password.equals(pass)) {
			return true;
		} else {
			return false;
		}
    }

	public static boolean userExists(String username) {
		System.out.println("BizLogic userExists");
		if (null != userDB.readUser(username)) {
			System.out.println("user exists");
			return true;
		} else {
			System.out.println("user does not exist");
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

	public static boolean createUser(String username, String name, String rawPass, String roleStr) {
		if (userExists(username)) {
			System.out.println("user exists: bizLogic");
			return false;
		} else {
			System.out.println("BizLogic createUser");
			UserRoles role = UserRoles.Guest;
			if (roleStr.equals("Admin")) {
				role = UserRoles.Admin;
			} else if (roleStr.equals("Reporter")) {
				role = UserRoles.Reporter;
			} else if (roleStr.equals("Subscriber")) {
				role = UserRoles.Subscriber;
			}
			String passHash = PassHash.hash(rawPass);
			userDB.createUser(new User(username, name, passHash, role));
			return true;
		}
	}

	public static int getNextPostID() {
		return storyDB.getNewPostID();
	}

	public static boolean storyExists(int postID) {
		System.out.println("BizLogic storyExists");
		if (null != storyDB.readStory(postID)) {
			System.out.println("story exists");
			return true;
		} else {
			System.out.println("story does not exist");
			return false;
		}
	}

	public static boolean createStory(String title, String author, int postID, String content, boolean subscriberOnly) {
		if (storyExists(postID)) {
			return false;
		} else {
			System.out.println("BizLogic create story");
			storyDB.createStory(new Story(author, title, content, subscriberOnly, postID));
			return true;
		}
	}
}
