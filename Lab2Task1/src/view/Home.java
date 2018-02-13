import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
    @author Dillon Dickerson
    @version 2/8/18
 **/

public class Home extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {

        HttpSession session = req.getSession();
        PrintWriter out = res.getWriter();
		res.setContentType("text/html");
        String loginUrl =  req.getContextPath()+"/ctrl/?page=login";
        String logoutUrl = req.getContextPath()+"/ctrl/?page=logout";
        String newStoryUrl = req.getContextPath()+"/ctrl/?page=addStory";
        String homeUrl = req.getContextPath();
        boolean isLoggedIn = false;
        boolean isReporter = false;
        String username = "";
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

		out.println("<html>");

        out.println(PageTemplate.printHead());
        out.println("<body>");
        out.println(PageTemplate.printNavbar(isLoggedIn, isReporter, username, homeUrl, newStoryUrl, loginUrl, logoutUrl, role));
        // Print articles here
        if (role.equals("Guest")) {
            ArrayList<Story> storyList = Controller.bizLogicCtrl.getAllStories(true);
            for (int i = 0; i < storyList.size(); i++) {
                String title = storyList.get(i).getTitle();
                int postID = storyList.get(i).getPostID();
                String author = storyList.get(i).getAuthor();
                out.println(PageTemplate.printTitle((req.getContextPath()+"/ctrl/?page=article&postID=" + postID), title, author));
            }
        } else {
            if (role.equals("Subscriber")) {
                ArrayList<Story> allsStoryList = Controller.bizLogicCtrl.getAllStories(true);
                ArrayList<Story> subStoryList = Controller.bizLogicCtrl.getSubscriberStories(username);
                ArrayList<Integer> usedPostIDs = new ArrayList();
                for (int i=0; i < subStoryList.size(); i++) {
                    String title = subStoryList.get(i).getTitle();
                    int postID = subStoryList.get(i).getPostID();
                    String author = subStoryList.get(i).getAuthor();
                    usedPostIDs.add(postID);
                    out.println(PageTemplate.printTitle((req.getContextPath()+"/ctrl/?page=article&postID=" + postID), title, author));
                }
                for (int i=0; i < allsStoryList.size(); i++) {
                    if (usedPostIDs.indexOf(allsStoryList.get(i).getPostID()) == -1) {
                        String title = allsStoryList.get(i).getTitle();
                        int postID = allsStoryList.get(i).getPostID();
                        String author = allsStoryList.get(i).getAuthor();
                        out.println(PageTemplate.printTitle((req.getContextPath()+"/ctrl/?page=article&postID=" + postID), title, author));
                    }
                }
            } else if ((role.equals("Reporter") || role.equals("Admin"))) {
                ArrayList<Story> allsStoryList = Controller.bizLogicCtrl.getAllStories(true);
                ArrayList<Story> subStoryList = Controller.bizLogicCtrl.getAuthorStories(username);
                ArrayList<Integer> usedPostIDs = new ArrayList();
                for (int i=0; i < subStoryList.size(); i++) {
                    String title = subStoryList.get(i).getTitle();
                    int postID = subStoryList.get(i).getPostID();
                    String author = subStoryList.get(i).getAuthor();
                    usedPostIDs.add(postID);
                    out.println(PageTemplate.printTitle((req.getContextPath()+"/ctrl/?page=article&postID=" + postID), title, author));
                }
                for (int i=0; i < allsStoryList.size(); i++) {
                    if (usedPostIDs.indexOf(allsStoryList.get(i).getPostID()) == -1) {
                        String title = allsStoryList.get(i).getTitle();
                        int postID = allsStoryList.get(i).getPostID();
                        String author = allsStoryList.get(i).getAuthor();
                        out.println(PageTemplate.printTitle((req.getContextPath()+"/ctrl/?page=article&postID=" + postID), title, author));
                    }
                }
            }
        }
        out.println("</body>");

        out.println("</html>");

    }
}
