import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.io.*;
public class User_serv extends HttpServlet
{
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException
	{
		res.setContentType("text/html");
    	PrintWriter out = res.getWriter();
    	String JDBC_DRIVER="oracle.jdbc.driver.OracleDriver";
    	String DB_URL="jdbc:oracle:thin:@localhost:1521:xe";
			String name=req.getParameter("name");
			ServletContext context=getServletContext();
    	context.setAttribute("user",name);
	HttpSession session=req.getSession(false);
	if(session==null)
	{
		res.sendRedirect("index.html");
	}
		try
		{
			Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL,"dasarada","dachi");
            Statement stmt = conn.createStatement();
            String sql1="select res_no,res_name,res_type,res_capacity,res_status from resources";
            ResultSet rs1=stmt.executeQuery(sql1);
						out.print("<html><link rel=\"stylesheet\" href=\"user.css\">");
						out.print("<center><h1 style=\"font-family:goudy old style;font-size:300%;color:#ff6600;\">"+name+"</h1><hr>");
						out.print("<h2 style=\"font-family:arial black;\">Welcome</h2><body>");
            out.print("<form method=\"post\" action=\"request1_page\">");
            out.print("<table><tr><th>Resource Number</th><th>Resource Name</th><th>Resource Type</th><th>Resource Capacity</th><th>Resource Status</th><th>Required Date</th></tr>");

            while(rs1.next())
            {
            		out.print("<tr>");
            		out.print("<td>"+rs1.getInt("res_no")+"</td>");
            		out.print("<td>"+rs1.getString("res_name")+"</td>");
            		out.print("<td>"+rs1.getString("res_type")+"</td>");
            		out.print("<td>"+rs1.getInt("res_capacity")+"</td>");
            		out.print("<td>"+rs1.getInt("res_status")+"</td>");
								int temp=rs1.getInt("res_no");
								out.print("<td><input type=\"date\" name=\""+temp+"\"/></td>");
            		out.print("<td><button type=\"submit\" name=\"request\" value=\""+rs1.getInt("res_no")+"\">REQUEST</button></td>");

            }
            out.print("</table>");
            out.print("</form></center>");
						out.print("<h3><center>History</center></h3>");
						String username=name;
						username="\'"+username+"\'";
						//String date="date";
						//out.print(username);
						String sql2="select allochistkey,res_name,alloc_time,status from allocate_hist where user_name="+username;
						ResultSet rs2=stmt.executeQuery(sql2);
						out.print("<center><table><tr><th>S No</th><th>Resource Name</th><th>Allocating time</th><th>Status</th></tr>");
						while(rs2.next())
						{
							out.print("<tr>");
							out.print("<td>"+rs2.getInt("allochistkey")+"</td>");
							out.print("<td>"+rs2.getString("res_name")+"</td>");
							java.sql.Date date=rs2.getDate("alloc_time");
							out.print("<td>"+date+"</td>");
							out.print("<td>"+rs2.getString("status")+"</td>");
							out.print("</tr>");
						}
						out.print("</table></center></body></html>");
		}
		catch(Exception e)
		{
			out.print(e);
		}
	}
}
