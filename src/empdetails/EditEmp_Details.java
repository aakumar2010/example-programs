package empdetails;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditEmp_Details")
public class EditEmp_Details extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditEmp_Details() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		Map<String, String[]> parameterMap = request.getParameterMap();
		String first_name = parameterMap.get("userID")[0].toString();
		try {
			Connection con = null;
			con = openConnection();
			
			ResultSet rs = loadUser(first_name, con);
			rs.next();
			out.append("<p align=center>Save User: </p>");
			out.append("</br>" );
			out.append("<form name='Per' action='/Test2/Emp_Details' method='post'>"
					+ "<table>"
					+ "<tbody>"
					+ "<tr><td>first_name: </td>"
					+ "<td><input type='text' name='first_name' value='"+ rs.getString(1)+"' />"
					+ "</tr>"
					+ "<tr><td>last_name: </td>"
					+ "<td><input type='text' name='last_name' value='"+ rs.getString(2)+"' />"
					+ "</tr>"
					+"<tr><td>phone: </td>"
					+ "<td><input type='text' name='phone' value='"+ rs.getString(3)+"' />"
					+ "</tr>"
					+ "<tr><td>email: </td>"
					+ "<td><input type='text' name='email' value='"+ rs.getString(4)+"' />"
					+ "</tr>"
					+"<tr><td>city: </td>"
					+ "<td><input type='text' name='city' value='"+ rs.getString(5)+"' />"
					+ "</tr>"
					+"<tr><td>state: </td>"
					+ "<td><input type='text' name='state' value='"+ rs.getString(6)+"' />"
					+ "</tr>"
					+"<tr><td>pin: </td>"
					+ "<td><input type='text' name='pin' value='"+ rs.getString(7)+"' />"
					+ "</tr>"
					+ "<tr><td>&nbsp;</td>"
					+ "<td>"
					+ "<input type='hidden' name='userID' value='"+ rs.getString(1) +"'/>"
					+ "<input type='hidden' name='action' value='update'/>"
					+ "<input type='submit' name='Submit' value='Save User' >"
					+ "</tr></tbody>"
					+ "</table>");

			
			//printEditEmpdetailsLink(out,rs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private ResultSet loadUser(String userID,Connection con) throws SQLException {
		String qry = "SELECT * FROM anil.emp_details where first_name=?";
		PreparedStatement ps = con.prepareStatement(qry);
		ps.setString(1, userID);
		ResultSet rs = ps.executeQuery();
		return rs;
	}



	private Connection openConnection() throws ClassNotFoundException, SQLException {
		Connection con;
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/anil", "root", "root");
		System.out.println("connection success");
		return con;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
