import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import javax.sql.*;
import java.sql.*;
public class Admin_serv extends HttpServlet
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
		{res.setContentType("text/html");


		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn=null;
		conn =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","dasarada","dachi");
		//out.print("Connected");
            Statement stmt = conn.createStatement();
            String sql1="select requests.req_key,requests.user_name,requests.res_no,requests.req_date,resources.res_name,userstb.no_of_grants,userstb.no_of_requests,userstb.prio from requests,resources,userstb where userstb.user_name=requests.user_name and requests.res_no=resources.res_no";
						out.print("<html><link rel=\"stylesheet\" href=\"admin.css\">");
						out.print("<center>");
						out.print("<h1 style=\"font-family:goudy old style;font-size:300%;color:#ff6600;\">Administrator</h1></center><hr></head><body>");
						out.print("<center>");
						out.print("<h2 style=\"font-family:arial black;\">Welcome</h2>");
						out.print("<h2 style=\"font-family:rockwell\">Here are the Requests</h2>");
            out.print("<table><tr><th>Request key</th><th>Username</th><th>No of Grants</th><th>No of requests</th><th>Priority</th><th>Resource Name</th><th>Date</th><th>Click Allocate</th></tr>");

						ResultSet rs1 = stmt.executeQuery(sql1);

            while(rs1.next())
            {
							out.print("<tr>");
							out.print("<form method=\"post\" action=\"allocate_page\">");
							int k=rs1.getInt("req_key");
							out.print("<td>"+k+"</td>");
							out.print("<td>"+rs1.getString("user_name")+"</td>");
							out.print("<td>"+rs1.getInt("no_of_grants")+"</td>");
							out.print("<td>"+rs1.getInt("no_of_requests")+"</td>");
							out.print("<td>"+rs1.getInt("prio")+"</td>");
							out.print("<td>"+rs1.getString("res_name")+"</td>");
							java.sql.Date date=rs1.getDate("req_date");
							out.print("<td>"+date+"</td>");
							out.print("<td><button type=\"submit\" value=\""+k+"\" name=\"allocate\"/>ALLOCATE</button></td></form>");
  						/*out.print("<tr>");
							String user_name=rs1.getString("user_name");
							int res_no=rs1.getInt("res_no");
							int req_key=rs1.getInt("req_key");
							String date="date";//rs1.getString("req_date");
							String sql3="select res_name from resources where res_no="+res_no;
							ResultSet rs3= stmt.executeQuery(sql3);
							rs3.next();
							String res_name=rs3.getString("res_name");
							out.print(res_name);
							String temp=user_name;
							out.print("<td>"+temp+"</td>");
							temp="\'"+temp+"\'";
							out.print(temp);
            	String sql2="select user_name,no_of_grants,no_of_requests,prio from userstb where user_name="+temp;
							//out.print("hi1");
            	ResultSet rs2 = stmt.executeQuery(sql2);
							//int no_of_grants=rs2.getInt("no_of_grants");
							//int no_of_requests=rs2.getInt("no_of_requests");
							//int prio=rs2.getInt("prio");
							//out.print("hi2");

							//out.print("hi3");

							//out.print("hi4");
            	while(rs2.next())
            	{
            		out.print("<td>"+rs2.getInt("no_of_grants")+"</td>");
            		out.print("<td>"+rs2.getInt("no_of_requests")+"</td>");
            		out.print("<td>"+rs2.getInt("prio")+"</td>");
            		out.print("<td>"+res_name+"</td>");
            		out.print("<td>"+date+"</td>");
            		out.print("<td><input type=\"submit\" value=\""+req_key+"\" name=\"allocate\"/></td>");
            	}*/
							out.print("<form method=\"post\" action=\"reject_serv\">");
							out.print("<td><button type=\"submit\" value=\""+k+"\" name=\"reject\"/>Reject</button></td></form>");
            	out.print("</tr>");

            }
            out.print("</table>");
						out.print("</center></body></html>");

		}
		catch(Exception e)
		{
			out.print(e);
		}
	}
}
