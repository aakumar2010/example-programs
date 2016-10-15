package persons;
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
@WebServlet("/Persons")
public class Persons extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Persons() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String PersonID=null;
		String FullName=null;
		String email=null;
		String phone=null;
		String City=null;
		String ZipCode=null;
		PrintWriter out=response.getWriter();
		Map<String, String[]> parameterMap = request.getParameterMap();
		
		System.out.println((parameterMap.get("PersonID"))[0].toString());
		System.out.println((parameterMap.get("FullName"))[0].toString());
		System.out.println((parameterMap.get("email"))[0].toString());
		System.out.println((parameterMap.get("phone"))[0].toString());
		System.out.println((parameterMap.get("City"))[0].toString());
		System.out.println((parameterMap.get("ZipCode"))[0].toString());
		
		out.append("Served at:").append(request.getContextPath());
		out.append("</br>"+(parameterMap.get("PersonID"))[0].toString());
		out.append("</br>"+(parameterMap.get("FullName"))[0].toString());
		out.append("</br>"+(parameterMap.get("email"))[0].toString());
		out.append("</br>"+(parameterMap.get("phone"))[0].toString());
		out.append("</br>"+(parameterMap.get("City"))[0].toString());
		out.append("</br>"+(parameterMap.get("ZipCode"))[0].toString());
		
		PersonID =parameterMap.get("PersonID")[0].toString();
		FullName =parameterMap.get("FullName")[0].toString();
		email =parameterMap.get("email")[0].toString();
		phone =parameterMap.get("phone")[0].toString();
		City =parameterMap.get("City")[0].toString();
		ZipCode =parameterMap.get("ZipCode")[0].toString();
		
		String sql = "insert into persons(PersonID,FullName,email,phone,City,ZipCode) values(?,?,?,?,?,?)";
		Connection con=null;
		//PreparedStatement ps=con.prepareStatement(sql);
		try{
			Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/anil", "root", "root");
            System.out.println("connection success");
            String qry = "SELECT * FROM anil.persons";
            Statement stmt = con.createStatement();
            PreparedStatement ps=con.prepareStatement(sql);
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
            	out.println("<td><form action=./persons method=get><input type=submit value=Edit></td></form>");
            	out.println("<td><form action=./persons method=get><input type=submit value=Delete></td></form>");
            	//out.print ("<td>[ <a href='controlpanel_deleteuser.html'>Delete</a> ]</td>");
            	out.println("</tr>");
            }
            out.print("</table></p>");
            //ps = con.prepareStatement(sql);
            ps.setString(1, PersonID);
            ps.setString(2, FullName);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, City);
            ps.setString(6, ZipCode);
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
