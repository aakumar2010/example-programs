package userdetails;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/UserDetails")
public class UserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserDetails() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		String UserName=null;
		String UserEmail=null;
		String UserPhone=null;
		PrintWriter out=response.getWriter();
		Map<String, String[]> parameterMap = request.getParameterMap();
		
		System.out.println((parameterMap.get("UserName"))[0].toString());
		System.out.println((parameterMap.get("UserEmail"))[0].toString());
		System.out.println((parameterMap.get("UserPhone"))[0].toString());
		
		out.append("Served at:").append(request.getContextPath());
		out.append("</br>"+(parameterMap.get("UserName"))[0].toString());
		out.append("</br>"+(parameterMap.get("UserEmail"))[0].toString());
		out.append("</br>"+(parameterMap.get("UserPhone"))[0].toString());
		
		UserName =parameterMap.get("UserName")[0].toString();
		UserEmail =parameterMap.get("UserEmail")[0].toString();
		UserPhone =parameterMap.get("UserPhone")[0].toString();
		
		String sql = "insert into user_details(UserName,UserEmail,UserPhone) values(?,?,?)";
		Connection con=null;
		PreparedStatement ps=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/anil", "root", "root");
            System.out.println("connection success");
            String qry = "SELECT * FROM anil.user_details";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(qry);
            
            out.println("<p alien='center'><table border=2>");
            ResultSetMetaData rsmd=rs.getMetaData();
            int colCount=rsmd.getColumnCount();
            out.println("<tr>");
            for(int i=0; i<colCount; i++)
            {
            	out.println("<th>"+rsmd.getColumnLabel(i+1)+"</th>");
            }
            out.println("</tr>");
            while(rs.next())
            {
            	out.println("<tr>");
            	for(int i=0; i<colCount; i++)
            	{
            		out.println("<td>" + rs.getString(i+1)+ "</td>");
            	}
            	out.println("</tr>");
            }
            out.print("</table></p>");
            ps = con.prepareStatement(sql);
            ps.setString(1, UserName);
            ps.setString(2, UserEmail);
            ps.setString(3, UserPhone);
            ps.executeUpdate();
            ps.close();
	}
		catch(Exception e)
		{	
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
