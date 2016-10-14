package greetings;
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

@WebServlet("/GreetUser")
public class GreetUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public GreetUser() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> parameterMap = request.getParameterMap();
		String email = null;
		String name = null;
		String phone = null;
		PrintWriter out=response.getWriter();
		
		System.out.println((parameterMap.get("name"))[0].toString());
		System.out.println((parameterMap.get("phone"))[0].toString());
		System.out.println((parameterMap.get("email"))[0].toString());
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getWriter().append("</br>"+(parameterMap.get("name"))[0].toString());
		response.getWriter().append("</br>"+(parameterMap.get("phone"))[0].toString());
		response.getWriter().append("</br>"+(parameterMap.get("email"))[0].toString());

		name =parameterMap.get("name")[0].toString();
		phone =parameterMap.get("phone")[0].toString();
		email = parameterMap.get("email")[0].toString();
		
		
		String sql = "insert into register(name,phone,email) values(?,?,?)";
        Connection con = null;
        PreparedStatement ps = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/anil", "root", "root");
            System.out.println("connection success");
            String qry = "SELECT * FROM anil.register";
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
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, email);
            ps.executeUpdate();
            ps.close();
	}
		catch(Exception e)
		{	
			e.printStackTrace();
		}
		}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}