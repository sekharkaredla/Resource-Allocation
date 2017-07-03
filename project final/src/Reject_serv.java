import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import javax.sql.*;
import java.sql.*;
public class Reject_serv extends HttpServlet
{
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException
	{
	PrintWriter out = res.getWriter();
	HttpSession session=req.getSession(false);
	if(session==null)
	{
		res.sendRedirect("index.html");
	}
		try
		{String req_key=req.getParameter("reject");
		res.setContentType("text/html");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn=null;
		conn =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","dasarada","dachi");
		Statement stmt = conn.createStatement();
		String sql1="select user_name from requests where req_key="+req_key;
		ResultSet rs1= stmt.executeQuery(sql1);
		rs1.next();
		String username=rs1.getString("user_name");
		username="\'"+username+"\'";

		String sql="select resources.res_name from resources,requests where requests.res_no=resources.res_no and requests.req_key="+req_key;
		ResultSet rs=stmt.executeQuery(sql);
		rs.next();
		String res_name=rs.getString("res_name");
		res_name="\'"+res_name+"\'";

		String sql2="update userstb set no_of_requests=no_of_requests+1 where user_name="+username;
		int rows=stmt.executeUpdate(sql2);

		String sql5="select nvl(max(allochistkey),0) from allocate_hist";
		ResultSet rs5=stmt.executeQuery(sql5);
		rs5.next();
		int allochistkey=rs5.getInt(1);

		String status="\'rejected\'";

		String sqld="select req_date from requests where req_key="+Integer.parseInt(req_key);
		ResultSet rsd=stmt.executeQuery(sqld);rsd.next();
		java.sql.Date date=rsd.getDate("req_date");
		String sql4="insert into allocate_hist(allochistkey,req_key,user_name,res_name,status,alloc_time) values("+(allochistkey+1)+","+Integer.parseInt(req_key)+","+username+","+res_name+","+status+","+"(select TO_DATE(\'"+date+"\',\'YYYY-MM-DD\') from dual)"+")";
		int k=stmt.executeUpdate(sql4);

		String sqlr="delete from requests where req_key="+req_key;
		rows=stmt.executeUpdate(sqlr);
		out.print("<html><h1><center>Rejected</center></h1><body>");
		out.print("<center><form action=\"logoutserv\" method=\"get\">");
		out.print("<button type=\"submit\">Logout</button></center>");
		out.print("</form></center></body></html>");
	}
	catch(Exception e)
	{
		out.print(e);
	}
}
}
