import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.xml.XMLSerializer;
/**
    @author Dillon Dickerson
    @version 2/10/18
 **/

 public class User {

     private UserRoles role;
     private String name;
     private String username;       //UID
     private String passHash;

     public User(String bson) {
         System.out.println(bson);
         JSONObject jsonObj = (JSONObject) JSONSerializer.toJSON(bson);
         this.name = jsonObj.getString("name");
         this.username = jsonObj.getString("username");
         this.passHash = jsonObj.getString("passHash");
         String jsonRole = jsonObj.getString("role");
         switch(jsonRole) {
             case "Subcriber":{
                 this.role = UserRoles.Subscriber;
                 break;
             }
             case "Reporter": {
                 this.role = UserRoles.Reporter;
             }
             case "Admin":{
                 this.role = UserRoles.Admin;
             }
             default: {
                 this.role = UserRoles.Guest;
             }
         }
     }

     public User(String username, String name, String passHash, UserRoles role) {
         this.username = username;
         this.name = name;
         this.passHash = passHash;
         this.role = role;
     }

     public UserRoles getRole() {
         return this.role;
     }

     public void setRole(UserRoles role) {
         this.role = role;
     }

     public String getName() {
         return this.name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public String getUserName() {
         return this.username;
     }

     public void setUserName(String username) {
         this.username = username;
     }

     public String getPassHash() {
         return this.passHash;
     }

     public void setPassHash(String passHash) {
         this.passHash = passHash;
     }

 }
