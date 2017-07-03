import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import javax.sql.*;
import java.sql.*;
public class Request1_serv extends HttpServlet
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
		{String resno=req.getParameter("request");
		ServletContext con=getServletContext();
		con.setAttribute("temp1",resno);
		res.setContentType("text/html");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn=null;
		conn =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","dasarada","dachi");
		Statement stmt = conn.createStatement();
		String sql1="select res_name from resources where res_no="+Integer.parseInt(resno);
		ResultSet rs1=stmt.executeQuery(sql1);
		rs1.next();
		String resname=rs1.getString("res_name");
		String datetmp=req.getParameter(resno);
		String date1=(String)req.getParameter(resno);
		con.setAttribute("tempdate",datetmp);
		String sql2="select res_name,to_char(alloc_time,\'yyyy-mm-dd\') as date2 from allocate_hist where status=\'allocated\'";
		ResultSet rs2=stmt.executeQuery(sql2);int flag=0;
		while(rs2.next())
		{
			out.print(date1);
			out.print(date1.equals(rs2.getString("date2")));
			if(resname.equals(rs2.getString("res_name")) && date1.equals(rs2.getString("date2")))
			{
				RequestDispatcher rd=req.getRequestDispatcher("reqwarn");
				rd.forward(req,res);
				flag=1;
			}
		}
		if(flag==0)
		{
			RequestDispatcher rd=req.getRequestDispatcher("request_page");
			rd.forward(req,res);
		}
	}
	catch(Exception e)
	{
		out.print(e);
	}
}
}
