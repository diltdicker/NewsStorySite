/**
    @author Dillon Dickerson
    @version 2/10/18
 **/

 public class PassHash {

     public static String hash(String rawPass) {
         String hash = "";
         for (int i = 0; i < rawPass.length(); i++) {
             hash += (int) rawPass.charAt(i);
         }
         return hash;
     }

 }
