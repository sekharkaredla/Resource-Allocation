import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import javax.sql.*;
import java.sql.*;
public class Reqwarn_serv extends HttpServlet
{
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException
	{
	PrintWriter out = res.getWriter();
	HttpSession session=req.getSession(false);
	String rsno=req.getParameter("request");
	if(session==null)
	{
		res.sendRedirect("index.html");
	}
	try
	{
		out.print("<html><h1><center>Warning!!!!</center></h1>");
				//	out.print("<h1>"+rsno+"</h1>");
		out.print("<center><h3>The requesting resource is already reserved on the same date you request</h3>");
		out.print("<h3>If you still want to request click REQUEST</h3>");
		out.print("<h3>If you want to cancel your request and logout click EXIT</h3><body>");
		out.print("<form method=\"post\" action=\"request_page\">");
		out.print("<button type=\"submit\" name=\"request\" value=\""+rsno+"\">request</button></form>");
		out.print("<form action=\"logoutserv\" method=\"get\">");
		out.print("<button type=\"submit\">Exit</button></center>");
		out.print("</form></body></center></html>");
	}
	catch(Exception e)
	{
		out.print(e);
	}
}
}
