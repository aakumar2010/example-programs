package empdetails;
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
@WebServlet("/EmpDetails")
public class EmpDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EmpDetails() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String first_name=null;
		String last_name=null;
		String phone=null;
		String email=null;
		String city=null;
		String state=null;
		String pin=null;
		PrintWriter out=response.getWriter();
		Map<String, String[]> parameterMap = request.getParameterMap();
		
		System.out.println((parameterMap.get("first_name"))[0].toString());
		System.out.println((parameterMap.get("last_name"))[0].toString());
		System.out.println((parameterMap.get("phone"))[0].toString());
		System.out.println((parameterMap.get("email"))[0].toString());
		System.out.println((parameterMap.get("city"))[0].toString());
		System.out.println((parameterMap.get("state"))[0].toString());
		System.out.println((parameterMap.get("pin"))[0].toString());
		
		out.append("Served at:").append(request.getContextPath());
		out.append("</br>"+(parameterMap.get("first_name"))[0].toString());
		out.append("</br>"+(parameterMap.get("last_name"))[0].toString());
		out.append("</br>"+(parameterMap.get("phone"))[0].toString());
		out.append("</br>"+(parameterMap.get("email"))[0].toString());
		out.append("</br>"+(parameterMap.get("city"))[0].toString());
		out.append("</br>"+(parameterMap.get("state"))[0].toString());
		out.append("</br>"+(parameterMap.get("pin"))[0].toString());
		
		first_name =parameterMap.get("first_name")[0].toString();
		last_name =parameterMap.get("last_name")[0].toString();
		phone =parameterMap.get("phone")[0].toString();
		email =parameterMap.get("email")[0].toString();
		city =parameterMap.get("city")[0].toString();
		state =parameterMap.get("state")[0].toString();
		pin =parameterMap.get("pin")[0].toString();
		
		String sql = "insert into emp_details(first_name,last_name,phone,email,city,state,pin) values(?,?,?,?,?,?,?)";
		Connection con=null;
		PreparedStatement ps=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/anil", "root", "root");
            System.out.println("connection success");
            String qry = "SELECT * FROM anil.emp_details";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(qry);
           // ResultSetMetaData rsmd=rs.getMetaData();
            
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
            ps.setString(1, first_name);
            ps.setString(2, last_name);
            ps.setString(3, phone);
            ps.setString(4, email);
            ps.setString(5, city);
            ps.setString(6, state);
            ps.setString(7, pin);
            ps.executeUpdate();
            ps.close();
	}
		catch(Exception e)
		{	
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
