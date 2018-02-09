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
public class Index extends HttpServlet {

    /*public void service(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {

        HttpSession session = req.getSession();

    }*/

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		res.setStatus(200);
		out.println("<html>");
		out.println("Um, why are you here?");
		out.println("Get back to where you belong");
		out.println("<a href=\"/task1\">Where you belong</a>");
        out.println("</html>");
    }
}
