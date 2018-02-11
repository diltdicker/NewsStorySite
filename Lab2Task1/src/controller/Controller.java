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

    public static void test() {
        System.out.println("testing helllo");
        //System.out.println(other);
        System.out.println(index++);
    }

    public static Pages handle;
    final private static String[] pageList = {"/index", "/login", "/edit", "/delete", "/edit"};

    public void init() throws ServletException {
        //ServletContext srvltCon = getServletContext();
        System.out.println("Controller Started");
        //other = Pages.ERROR;
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
                res.setStatus(200);
                System.out.println(req.getParameter("username"));
                System.out.println(req.getParameter("password"));       //works

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
            default:{
                handle = Pages.INDEX;
                res.setStatus(404);
            }
        }
        doHandle(req, res);
    }
}
