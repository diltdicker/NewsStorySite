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

     public Story(int postID, String author, String title, String content, boolean subscriberOnly) {
         this.postID = postID;
         this.author = author;
         this.title = title;
         this.content = content;
         this.subscriberOnly = subscriberOnly;
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

 }
