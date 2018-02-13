import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.xml.XMLSerializer;
import java.util.ArrayList;
/**
    @author Dillon Dickerson
    @version 2/10/18
 **/

 public class Story {

     private String author;     //UID of User
     private String title;
     private int postID;        //UID of Story
     private String content;
     private boolean subscriberOnly;
     private ArrayList<String> subscriberList;

     public Story(String author, String title, String content, boolean subscriberOnly, int postID) {
         this.author = author;
         this.title = title;
         this.content = content;
         this.subscriberOnly = subscriberOnly;
         this.subscriberList = new ArrayList();
         this.postID = postID;
         System.out.println("TITLE: " + this.title);
         // inital creation doesnot include a Subscriber list
     }

     public Story(String bson) {
         System.out.println(bson);
         JSONObject jsonObj = (JSONObject) JSONSerializer.toJSON(bson);
         this.postID = jsonObj.getInt("postID");
         this.author = jsonObj.getString("author");
         this.title = jsonObj.getString("title");
         this.content = jsonObj.getString("content");
         this.subscriberOnly = jsonObj.getBoolean("subscriberOnly");
         this.subscriberList = (ArrayList<String>) JSONSerializer.toJava(jsonObj.getJSONArray("subscriberList"));
         System.out.println("TITLE: " + this.title);
     }

     public String getAuthor() {
         return this.author;
     }

     public void setAuthor(String author) {
         this.author = author;
     }

     public String getTitle() {
         return this.title;
     }

     public void setTitle(String title) {
         this.title = title;
     }

     public int getPostID() {
         return this.postID;
     }

     public void setPostID(int postID) {
         this.postID = postID;
     }

     public String getContent() {
         return this.content;
     }

     public void setContent(String content) {
         this.content = content;
     }

     public boolean getSubscriberOnly() {
         return this.subscriberOnly;
     }

     public void setSubscriberOnly(boolean subscriberOnly) {
         this.subscriberOnly = subscriberOnly;
     }

     public ArrayList getSubscriberList() {
         return this.subscriberList;
     }

     public void addSubscriberList(String username) {
         this.subscriberList.add(username);
     }

     public boolean removeSubscriberList(String username) {
         int index = this.subscriberList.indexOf(username);
         if (index == -1) {
             return false;
         } else {
             this.subscriberList.remove(index);
             return true;
         }
     }

 }
