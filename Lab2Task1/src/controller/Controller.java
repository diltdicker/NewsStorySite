import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
/**
    @author Dillon Dickerson
    @version 2/8/18
 **/
 
public class Controller extends HttpServlet {

    public void init() throws ServletException {
        //ServletContext srvltCon = getServletContext();
    }

    private void doHandle(HttpServletRequest req, HttpServletResponse res) {

        HttpSession session = req.getSession();

    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) {

    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		res.setStatus(405);
		out.println("<html>");
		out.println("Um, why are you here?");
		out.println("Get back to where you belong");
		out.println("<a href=\"/task1\">Where you belong</a>");
        out.println("</html>");
    }
}
