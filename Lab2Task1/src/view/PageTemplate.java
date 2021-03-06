public class PageTemplate {

    public static String printHead() {
        String head = "<head>" +
        "<style media=\"screen\">" +
        "</style>" +
        "<title>News Story Site</title></head>";
        return head;
    }

    public static String printTitle(String url, String title, String author) {
        String pageTitle = "<div class=\"title\"><hr>" +
        "<a href=" + url + ">" + title + "</a>" +
            "<p>" + author + "</p>"+
            "</div>";
        return pageTitle;
    }

    public static String printArticle(int postID, String title, String content, String author, UserRoles role, String url) {
        String article = "<div class=\"article\">" +
            "<div class=\"title\">" +
                "<hr>" +
                "<h3>"+title+"<h3>" +
                "<p>" + author + "</p>" +
            "</div>" +
            "<div class=\"story\">" +
                "<p>" + content + "</p>" +
            "</div>" +
            "<div class=\"options\">" +
                "<form class=\"form\" action=" + url + " method=\"GET\">" +
                "<label name=\"postID\" value="+postID+">PostID: "+postID+"</label>";
        if (role == UserRoles.Subscriber) {
            article += "<button type=\"submit\" name=\""+url+"articleAction\" value=\"Subscribe\">Subcribe</button>";
        } else if (role == UserRoles.Reporter || role == UserRoles.Admin) {
            article += "<br><button type=\"submit\" name=\""+url+"articleAction\" value=\"Edit\">Edit</button>";
            article += "<button type=\"submit\" name=\""+url+"articleAction\" value=\"Delete\">Delete</button>";
        }
        article += "</form></div></div>";
        return article;
    }

    public static String printNavbar(boolean isLoggedIn, boolean isReporter, String username, String homeUrl, String newStoryUrl, String loginUrl, String logoutUrl, String role) {
        String navbar = "<div class=\"navbar\"><ul><li><a href="+homeUrl+">Home</a></li>";
        if (isLoggedIn && isReporter) {
            navbar += "<li><a href="+newStoryUrl+">New Story</a></li>";
        }
        if (isLoggedIn) {
            navbar += "<li><a href="+logoutUrl+">Logout</a></li>";
            navbar += "<li>"+username+"</li>";
            navbar += "<li>"+role+"</li>";
        } else {
            navbar += "<li><a href="+loginUrl+">Login</a></li>";
        }
        navbar += "</ul></div>";
        return navbar;
    }

    public static String printEditArticle(String username, String title, boolean isSubscriberOnly, String content, String url) {
        String area = "<hr>" +
        "<form class=\"form\" action="+url+" method=\"post\">" +
            "<input type=\"text\" name=\"title\" placeholder="+title+" required>" +
            "<br>" +
            "<label name=\"author\">Author: "+username+"</label>" +
            "<br>" +
            "<label for=\"isSubscriberOnly\">Is Subcriber Only: </label>" +
            "<input type=\"checkbox\" name=\"isSubscriberOnly\" value=\"true\">" +
            "<br>" +
            "<textarea name=\"content\" cols=\"50\" rows=\"10\"></textarea>" +
            "<br>" +
            "<button type=\"submit\" name=\"button\">Submit</button>" +
        "</form>";
        return area;
    }

    public static String printAddArticle(String username, String url) {
        String area = "<hr>" +
        "<form class=\"form\" action="+url+" method=\"post\">" +
            "<input type=\"text\" name=\"title\" placeholder=\"title\" required>" +
            "<br>" +
            "<label name=\"author\">Author: "+username+"</label>" +
            "<br>" +
            "<label for=\"subscriberOnly\">Is Subcriber Only: </label>" +
            "<input type=\"checkbox\" name=\"subscriberOnly\" value=\"true\">" +
            "<br>" +
            "<textarea name=\"content\" cols=\"50\" rows=\"10\"></textarea>" +
            "<br>" +
            "<button type=\"submit\" name=\"button\">Submit</button>" +
        "</form>";
        return area;
    }

    public static String printNewUser(String url, boolean passMatch, boolean usernameTaken) {
        String form = "<form class=\"form\" action="+url+" method=\"post\">";
        if (!passMatch) {
            form += "<p>password doesn't match</p><br>";
        }
        if (usernameTaken) {
            form += "<p>the username is already taken</p><br>";
        }
            form += "<label for=\"username\">Username:</label><br>"+
            "<input type=\"text\" name=\"username\" required><br>"+
            "<label for=\"name\">Name:</label><br>"+
            "<input type=\"text\" name=\"name\" required><br>"+
            "<label for=\"password1\">Password:</label><br>"+
            "<input type=\"password\" name=\"password1\" required><br>"+
            "<input type=\"password\" name=\"password2\" required><br>"+
            "<label for=\"role\">User Type: </label>"+
            "<select class=\"select\" name=\"role\">"+
                "<option value=\"Subscriber\" selected>Subscriber</option>"+
                "<option value=\"Reporter\">Reporter</option>"+
                "<option value=\"Admin\">Admin</option>"+
            "</select><br>"+
            "<button type=\"submit\" name=\"button\">Submit</button>"+
        "</form>";
        return form;
    }

    public static String printLoginForm(String url) {
        String form = "<form class=\"form\" action="+url+" method=\"post\">"+
            "<input type=\"text\" name=\"username\" placeholder=\"username\" required><br>"+
            "<input type=\"password\" name=\"password\" placeholder=\"password\" required>"+
            "<button type=\"submit\" name=\"button\">Login</button>"+
        "</form>";
        return form;
    }

    public static String printLoginResult(String url, String newUserUrl, int statusCode) {
        String form = "<div class=\"form\">"+
            "<h3>Error "+statusCode+"</h3>"+
            "<p>Your login attempt was unsucsessful</p>"+
            "<p>click here to try again </p><a href="+url+">LOGIN</a>"+
            "<p>click here to create a new account </p><a href="+newUserUrl+">Nw</a>"+
        "</div>";
        return form;
    }

}
