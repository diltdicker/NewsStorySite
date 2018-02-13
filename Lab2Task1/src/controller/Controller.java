import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import java.io.IOException;
import java.util.ArrayList;
/**
    @author Dillon Dickerson
    @version 2/8/18
 **/


public class Controller extends HttpServlet {

    //public static Pages other;
    public static int index = 0;
    public static BizLogic bizLogicCtrl;

    public static void test() {
        System.out.println("testing helllo");
        //System.out.println(other);
        System.out.println(index++);
    }

    public static Pages handle;
    final private static String[] pageList = {"/index", "/login", "/editStory", "/delete", "/addStory", "/loginResult", "/article",
        "/newUser"};

    public void init() throws ServletException {
        //ServletContext srvltCon = getServletContext();
        System.out.println("Controller Started");
        //other = Pages.ERROR;
        this.bizLogicCtrl = new BizLogic();
    }

    public void destroy() {
        this.bizLogicCtrl.destroy();
        System.out.println("Controller Destroyed");
    }

    private void doHandle(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        HttpSession session = req.getSession();
        String page = "";
        switch (handle) {
            case INDEX: {
                page = pageList[0];
                break;
            }
            case LOGIN: {
                page = pageList[1];
                break;
            }
            case EDIT: {
                page = pageList[2];
                break;
            }
            case DELETE: {
                page = pageList[3];
                break;
            }
            case ADD: {
                page = pageList[4];
                break;
            }
            case LOGINRESULT: {
                boolean isLoggedIn = BizLogic.login(req.getParameter("username"), req.getParameter("password"));
                session.setAttribute("isLoggedIn", isLoggedIn);
                if (isLoggedIn) {
                    session.setAttribute("username", req.getParameter("username"));
                    session.setAttribute("role", BizLogic.getRole(req.getParameter("username")).toString());
                    page = pageList[0];
                } else {
                    System.out.println("login failed");
                    session.setAttribute("isLoggedIn", false);
                    res.setStatus(400);
                    page = pageList[5];
                }
                break;
            }
            case LOGOUT: {
                session.setAttribute("isLoggedIn", false);
                session.setAttribute("username", "Guest");
                session.setAttribute("role", UserRoles.Guest.toString());
                res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                page = pageList[0];
                break;
            }
            case VIEW: {
                page = pageList[6];
                break;
            }
            case NEWUSER :{
                page = pageList[7];
                break;
            }
            case ERROR: {
                page = "/error";
                break;
            }
            default: {
                page = "/error";
            }
        }
        req.getRequestDispatcher(page).forward(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pageFlag;
        HttpSession session = req.getSession();
        if (req.getParameter("page") == null) {
            pageFlag = "";
        } else {
            pageFlag = req.getParameter("page");
        }
        switch (pageFlag) {
            case "":{
                handle = Pages.ERROR;
                res.setStatus(405);
                break;
            }
            case "home":{
                handle = Pages.ERROR;
                res.setStatus(405);
                break;
            }
            case "login":{
                handle = Pages.LOGIN;
                res.setStatus(404);
                break;
            }
            case "loginResult":{
                res.setStatus(200);
                System.out.println(req.getParameter("username"));
                System.out.println(req.getParameter("password"));       //works
                handle = Pages.LOGINRESULT;
                break;
            }
            case "editStory":{
                if (null != session.getAttribute("isLoggedIn")) {
                    boolean isLoggedIn = (boolean) session.getAttribute("isLoggedIn");
                    if(isLoggedIn) {
                        String username = (String) session.getAttribute("username");
                        String role = (String) session.getAttribute("role");
                        System.out.println("role:" + role);
                        if (role.equals("Reporter") || role.equals("Admin")) {
                            session.setAttribute("pageAction", "");
                            handle = Pages.VIEW;
                            res.setStatus(200);
                        } else if (role.equals("Subscriber")) {

                        } else {
                            handle = Pages.ERROR;
                            res.setStatus(401);
                        }
                    } else {
                        handle = Pages.ERROR;
                        res.setStatus(401);
                    }
                } else {
                    handle = Pages.ERROR;
                    res.setStatus(401);
                }
                break;
            }
            case "addStory":{
                if (null != session.getAttribute("isLoggedIn")) {
                    boolean isLoggedIn = (boolean) session.getAttribute("isLoggedIn");
                    if(isLoggedIn) {
                        String role = (String) session.getAttribute("role");
                        System.out.println("role:" + role);
                        if (role.equals("Reporter") || role.equals("Admin")) {
                            session.setAttribute("pageAction", "");
                            handle = Pages.INDEX;
                            // add Story here
                            try {
                                res.setStatus(200);
                                int postID = bizLogicCtrl.getNextPostID();

                                String title = (String) req.getParameter("title");
                                System.out.println("title "+title);

                                String content = (String) req.getParameter("content");
                                System.out.println("content "+content);

                                String subscriberOnlyStr = "false";
                                if (null != req.getParameter("subscriberOnly")) {
                                    subscriberOnlyStr = (String) req.getParameter("subscriberOnly");
                                }
                                System.out.println("subscriberOnly "+subscriberOnlyStr);

                                String username = (String) session.getAttribute("username");
                                System.out.println("username: "+username);
                                boolean subscriberOnly = false;
                                if (subscriberOnlyStr.equals("true")) {
                                    subscriberOnly = true;
                                }
                                if (bizLogicCtrl.createStory(title, username, postID, content, subscriberOnly)) {
                                    // Sucsses
                                    System.out.println("Story created");
                                } else {
                                    handle = Pages.ERROR;
                                    res.setStatus(400);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            handle = Pages.ERROR;
                            res.setStatus(401);
                        }
                    } else {
                        handle = Pages.ERROR;
                        res.setStatus(401);
                    }
                } else {
                    handle = Pages.ERROR;
                    res.setStatus(401);
                }
                break;
            }
            case "deleteStory":{
                break;
            }
            case "newUser" :{
                res.setStatus(200);
                System.out.println(req.getParameter("username"));
                System.out.println(req.getParameter("name"));
                System.out.println(req.getParameter("password1"));
                System.out.println(req.getParameter("password2"));
                System.out.println(req.getParameter("role"));
                String pass1 = req.getParameter("password1");
                String pass2 = req.getParameter("password2");
                if (pass1.equals(pass2)) {
                    session.setAttribute("passMatch", true);
                    handle = Pages.INDEX;
                    // create User
                    String username = req.getParameter("username");
                    String roleStr = req.getParameter("role");
                    String name = req.getParameter("name");
                    if (bizLogicCtrl.createUser(username, name, pass1, roleStr)) {
                        session.setAttribute("usernameTaken", false);
                    } else {
                        // username taken
                        handle =Pages.NEWUSER;
                        System.out.println("username taken set");
                        session.setAttribute("usernameTaken", true);
                    }
                } else {
                    System.out.println("passwords do not match set");
                    session.setAttribute("passMatch", false);
                    handle = Pages.NEWUSER;
                }

                break;
            }
            case "article" : {
                handle = Pages.ERROR;
                res.setStatus(405);
                break;
            }
            default:{
                handle = Pages.ERROR;
                res.setStatus(404);
            }
        }
        doHandle(req, res);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pageFlag;
        HttpSession session = req.getSession();
        if (req.getParameter("page") == null) {
            pageFlag = "";
        } else {
            pageFlag = req.getParameter("page");
        }
        switch (pageFlag) {
            case "":{
                handle = Pages.INDEX;
                res.setStatus(200);
                break;
            }
            case "home":{
                handle = Pages.INDEX;
                res.setStatus(200);
                break;
            }
            case "login":{
                handle = Pages.LOGIN;
                res.setStatus(200);
                break;
            }
            case "loginResult":{
                break;
            }
            case "editStory":{
                if (null != session.getAttribute("isLoggedIn")) {
                    boolean isLoggedIn = (boolean) session.getAttribute("isLoggedIn");
                    if(isLoggedIn) {
                        String role = (String) session.getAttribute("role");
                        System.out.println("role:" + role);
                        int postID = Integer.parseInt(req.getParameter("postID"));
                        String username = (String) session.getAttribute("username");
                        if (role.equals("Reporter") || role.equals("Admin")) {
                            if (req.getParameter("articleAction") != null) {
                                String action = (String) req.getParameter("articleAction");
                                if (action.equals("Edit")) {
                                    handle = Pages.EDIT;
                                } else if (action.equals("Delete")) {
                                    bizLogicCtrl.removeStory(postID);
                                    handle = Pages.INDEX;
                                    res.setStatus(200);
                                } else {
                                    handle = Pages.INDEX;
                                    res.setStatus(200);
                                }
                            }
                        } else if (role.equals("Subscriber")) {
                            if (req.getParameter("articleAction") != null) {
                                String action = (String) req.getParameter("articleAction");
                                if (action.equals("Subscribe")) {
                                    Story story = bizLogicCtrl.getStory(postID);
                                    story.addSubscriberList(username);
                                } else {
                                    handle = Pages.INDEX;
                                    res.setStatus(200);
                                }
                            }
                        }
                    } else {
                        handle = Pages.ERROR;
                        res.setStatus(401);
                    }
                } else {
                    handle = Pages.ERROR;
                    res.setStatus(401);
                }
                break;
            }
            case "addStory":{
                if (null != session.getAttribute("isLoggedIn")) {
                    boolean isLoggedIn = (boolean) session.getAttribute("isLoggedIn");
                    if(isLoggedIn) {
                        String role = (String) session.getAttribute("role");
                        System.out.println("role:" + role);
                        if (role.equals("Reporter") || role.equals("Admin")) {
                            session.setAttribute("pageAction", "Add");
                            handle = Pages.ADD;
                            res.setStatus(200);
                        } else {
                            handle = Pages.ERROR;
                            res.setStatus(401);
                        }
                    } else {
                        handle = Pages.ERROR;
                        res.setStatus(401);
                    }
                } else {
                    handle = Pages.ERROR;
                    res.setStatus(401);
                }
                break;
            }
            case "deleteStory":{
                break;
            }
            case "logout": {
                res.setStatus(200);
                handle = Pages.LOGOUT;
                break;
            }
            case "newUser": {
                handle = Pages.NEWUSER;
                break;
            }
            case "article": {
                if (req.getParameter("postID") != null) {
                    try {
                        int postID = Integer.parseInt(req.getParameter("postID"));
                        if (bizLogicCtrl.storyExists(postID)) {
                            // logic for credentials
                            Story story = bizLogicCtrl.getStory(postID);
                            String username = "";
                            String author = story.getAuthor();
                            if (null != session.getAttribute("username")) {
                                username = (String) session.getAttribute("username");
                            }
                            if (story.getSubscriberOnly()) {
                                ArrayList<String> subscriberList = story.getSubscriberList();
                                if (subscriberList.indexOf(username) != -1) {
                                    handle = Pages.VIEW;
                                    res.setStatus(200);
                                } else {
                                    handle = Pages.ERROR;
                                    res.setStatus(401);
                                }
                            } else {
                                handle = Pages.VIEW;
                                res.setStatus(200);
                            }
                            if (author.equals(username)) {
                                handle = Pages.VIEW;
                                res.setStatus(200);
                            }
                        } else {
                            handle = Pages.ERROR;
                            res.setStatus(404);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        handle = Pages.ERROR;
                        res.setStatus(404);
                    }
                } else {
                    handle = Pages.ERROR;
                    res.setStatus(400);
                }
                break;
            }
            default:{
                handle = Pages.INDEX;
                res.setStatus(404);
            }
        }
        doHandle(req, res);
    }
}
