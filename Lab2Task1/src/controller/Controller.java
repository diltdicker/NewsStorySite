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
    final private static String[] pageList = {"/index", "/login", "/edit", "/delete", "/edit", "/loginResult", "/article",
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
                session.setAttribute("pageAction", Pages.EDIT);
                break;
            }
            case DELETE: {
                page = pageList[3];
                break;
            }
            case ADD: {
                page = pageList[4];
                session.setAttribute("pageAction", Pages.ADD);
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
                break;
            }
            case "addStory":{
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
                break;
            }
            case "addStory":{
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
            default:{
                handle = Pages.INDEX;
                res.setStatus(404);
            }
        }
        doHandle(req, res);
    }
}
