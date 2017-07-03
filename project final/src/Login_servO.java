import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
public class  Login_servO extends HttpServlet
 {

  public void doPost(HttpServletRequest req, HttpServletResponse res)
  {
    try{

    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
	   String n=req.getParameter("name");
    String p=req.getParameter("pass");

    //out.print("<title>login</title>");
  	Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn=null;
		conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","dasarada","dachi");
    //out.print("connection estabished successfully");
              Statement stmt = conn.createStatement();
               String sql = "SELECT username,pass,flag FROM credential";
         ResultSet rs = stmt.executeQuery(sql);int flag=0;
         while(rs.next())
         {
    if(n.equals(rs.getString("username"))&&p.equals(rs.getString("pass")))
    {
        HttpSession session=req.getSession();
        session.setAttribute("name",n);
        if(rs.getInt("flag")==1)
        {
        RequestDispatcher rd=req.getRequestDispatcher("adminserv");flag=1;
	//out.println("admin");
        rd.forward(req, res);
        }
    else{
        RequestDispatcher rd=req.getRequestDispatcher("userserv");flag=1;
	//out.println("user");
        rd.forward(req, res);
        }
      }
    }
    if(flag==0)
    {
       res.sendRedirect("index.html");
    }
     }catch(Exception e){System.out.println(e);}
 }
}
