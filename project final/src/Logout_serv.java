import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
public class Logout_serv extends HttpServlet
{
  public void service(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException
  {
    HttpSession session=req.getSession(false);
  	if(session==null)
  	{
  		res.sendRedirect("index.html");
  	}
    session.invalidate();
    res.sendRedirect("index.html");
  }
}
