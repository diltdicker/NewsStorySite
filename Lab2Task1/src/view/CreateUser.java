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

public class CreateUser extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {

        HttpSession session = req.getSession();
        PrintWriter out = res.getWriter();
		res.setContentType("text/html");
        String loginUrl =  req.getContextPath()+"/ctrl/?page=login";
        String logoutUrl = req.getContextPath()+"/ctrl/?page=logout";
        String newStoryUrl = req.getContextPath()+"/ctrl/?page=addStory";
        String newUserUrl = req.getContextPath()+"/ctrl/?page=newUser";
        String homeUrl = req.getContextPath();
        boolean isLoggedIn = false;
        boolean isReporter = false;
        String username = "username";
        String role = "Guest";
        if (null != session.getAttribute("isLoggedIn")) {
            isLoggedIn = (boolean) session.getAttribute("isLoggedIn");
            if(isLoggedIn) {
                username = (String) session.getAttribute("username");
                role = (String) session.getAttribute("role");
                System.out.println("role:" + role);
                if (role.equals("Reporter") || role.equals("Admin")) {
                    isReporter = true;
                }
            }
        }
        boolean passMatch = false;
        boolean usernameTaken = false;
        if (null != session.getAttribute("passMatch")) {
            passMatch = (boolean) session.getAttribute("passMatch");
        }
        if (null != session.getAttribute("usernameTaken")) {
            usernameTaken = (boolean) session.getAttribute("usernameTaken");
        }

		out.println("<html>");

        out.println(PageTemplate.printHead());
        out.println("<body>");
        out.println(PageTemplate.printNavbar(isLoggedIn, isReporter, username, homeUrl, newStoryUrl, loginUrl, logoutUrl, role));
        // Print content here
        out.println(PageTemplate.printNewUser(newUserUrl, passMatch, usernameTaken));

        out.println("</body>");

        out.println("</html>");
    }
}
