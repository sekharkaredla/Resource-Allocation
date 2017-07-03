import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
//import java.text.SimpleDateFormat;
public class Request_serv extends HttpServlet
{
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException
	{
      res.setContentType("text/html");
    	PrintWriter out = res.getWriter();
    	String JDBC_DRIVER="oracle.jdbc.driver.OracleDriver";
    	String DB_URL="jdbc:oracle:thin:@localhost:1521:xe";
			String res_no=req.getParameter("request");
			//out.print(res_no);
      //String date=(String)req.getParameter(res_no);
			//out.print(date);

			//String datetemp=context.getAttribute("tempdate");

		//out.print(username);
	HttpSession session=req.getSession(false);
	if(session==null)
	{
		res.sendRedirect("index.html");
	}
      try
      {
				ServletContext context=getServletContext();
				String username=(String)context.getAttribute("user");
				//java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String)req.getParameter(res_no));
		//out.print(req.getParameter(res_no))
				out.print("<html><link rel=\"stylesheet\" href=\"all.css\"><body>");
        Class.forName(JDBC_DRIVER);
        Connection conn = DriverManager.getConnection(DB_URL,"dasarada","dachi");
        Statement stmt = conn.createStatement();
        String sql1="select nvl(max(req_key),0) from requests";
        ResultSet rs1=stmt.executeQuery(sql1);
				rs1.next();

        String sql2="insert into requests(req_key,user_name,res_no,req_date) values("+(rs1.getInt(1)+1)+",\'"+username+"\',"+res_no+","+"(select TO_DATE(\'"+context.getAttribute("tempdate")+"\',\'YYYY-MM-DD\') from dual)"+")";
        int k=stmt.executeUpdate(sql2);
				out.print("<h2 style=\"font-family:goudy old style;font-size:300%;color:#ff6600;\"><center>Your Request had been submitted successfully</center></h2>");
				out.print("<br>");
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
