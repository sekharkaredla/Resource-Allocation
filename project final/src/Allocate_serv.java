import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.io.*;
public class Allocate_serv extends HttpServlet
{
  public void service(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException
  {
      res.setContentType("text/html");
    	PrintWriter out = res.getWriter();
    	String JDBC_DRIVER="oracle.jdbc.driver.OracleDriver";
    	String DB_URL="jdbc:oracle:thin:@localhost:1521:xe";
	HttpSession session=req.getSession(false);
	if(session==null)
	{
		res.sendRedirect("index.html");
	}
      try
      {
        String req_key=req.getParameter("allocate");
      //  out.print(req_key+" ");
        Class.forName(JDBC_DRIVER);
        Connection conn = DriverManager.getConnection(DB_URL,"dasarada","dachi");
        Statement stmt = conn.createStatement();
      //  out.print("Connected");
        String sql1="select user_name from requests where req_key="+req_key;
        ResultSet rs1= stmt.executeQuery(sql1);
        rs1.next();
        String username=rs1.getString("user_name");
      //  out.print("hi");

        username="\'"+username+"\'";
        //reqdate="\'"+reqdate+"\'";
      //  out.print(username);
        String sql="select resources.res_name from resources,requests where requests.res_no=resources.res_no and requests.req_key="+req_key;
        ResultSet rs=stmt.executeQuery(sql);
        rs.next();
        String res_name=rs.getString("res_name");
        //out.print("hi");
        String sql2="update userstb set no_of_grants=no_of_grants+1 where user_name="+username;
        int rows=stmt.executeUpdate(sql2);
        //out.print("hi");
        sql2="update userstb set no_of_requests=no_of_requests+1 where user_name="+username;
        rows=stmt.executeUpdate(sql2);
        //out.print("hi");
        res_name="\'"+res_name+"\'";
        String sql5="select nvl(max(allochistkey),0) from allocate_hist";
        ResultSet rs5=stmt.executeQuery(sql5);
        rs5.next();
        String status="\'allocated\'";
        int allochistkey=rs5.getInt(1);
        //out.print("hi");
        String sqld="select req_date from requests where req_key="+Integer.parseInt(req_key);
        ResultSet rsd=stmt.executeQuery(sqld);
        rsd.next();
        java.sql.Date date=rsd.getDate("req_date");
        //out.print("hi");
        String sql4="insert into allocate_hist(allochistkey,req_key,user_name,res_name,status,alloc_time) values("+(allochistkey+1)+","+Integer.parseInt(req_key)+","+username+","+res_name+","+status+","+"(select TO_DATE(\'"+date+"\',\'YYYY-MM-DD\') from dual)"+")";
        int k=stmt.executeUpdate(sql4);
        //out.print("hi");
        String sql3="delete from requests where req_key="+req_key;
        rows=stmt.executeUpdate(sql3);
        //out.print("hi");
        out.print("<html><link rel=\"stylesheet\" href=\"all.css\"><h1><center>Allocated</center></h1>");
        //out.print("<style>button {background-color: #4CAF50;color: black;cursor: pointer;width: 30%;}");

        //out.print("button:hover {opacity: 0.5;}</style>");
        out.print("<body><center><form action=\"logoutserv\" method=\"get\">");
        out.print("<center><button type=\"submit\">logout</button></center>");
        out.print("</form></center></body></html>");
      }
      catch(Exception e)
      {
        out.print(e);
      }
  }
}
