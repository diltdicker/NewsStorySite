import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
    @author Dillon Dickerson
    @version 2/8/18
 **/

public class ViewArticle extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {

        HttpSession session = req.getSession();
        boolean isLoggedIn = false;
        if (null != session.getAttribute("isLoggedIn")) {
            isLoggedIn = (boolean) session.getAttribute("isLoggedIn");
        }
        Controller.test();
        PrintWriter out = res.getWriter();
        out.println("<html><body>");
        if (isLoggedIn) {
            out.println("<p>" + session.getAttribute("username") + "</p><br>");
        }
        out.println("<form class=\"\" action=\" " + req.getContextPath() + "/ctrl/login?page=loginResult\" method=\"post\">" +
                    "<input type=\"text\" name=\"username\" placeholder=\"username\" required>" +
                    "<input type=\"text\" name=\"password\" placeholder=\"password\" required>" +
                    "<button type=\"submit\" name=\"button\">Login</button></form></body></html>");
    }
}
