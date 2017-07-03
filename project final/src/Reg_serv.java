import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import javax.sql.*;
import java.sql.*;
public class Reg_serv extends HttpServlet
{
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException
	{
	PrintWriter out = res.getWriter();
	///HttpSession session=req.getSession(false);
	String n=req.getParameter("uname");
 String p=req.getParameter("pswd");
 int flag=0;

	try
	{
		res.setContentType("text/html");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn=null;
		conn =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","dasarada","dachi");
		Statement stmt = conn.createStatement();
		int g=0;
		String sql1="insert into credential(username,pass,flag) values(\'"+n+"\',\'"+p+"\',"+flag+")";
		int m=stmt.executeUpdate(sql1);
		String sql2="insert into userstb(user_name,no_of_grants,no_of_requests) values(\'"+n+"\',"+g+","+g+")";
		m=stmt.executeUpdate(sql2);
		out.print("<html><h1>successfully registered</h1></html>");
	}
	catch(Exception e)
	{
		//out.print(e);
		out.print("<html><center><h1>Warning!!!</h1>");
		out.print("<h3>The username "+n+" is already present</h3>");
		out.print("<h3>please register with different username!!</h3>");
		out.print("</center></html>");
	}
}
}
