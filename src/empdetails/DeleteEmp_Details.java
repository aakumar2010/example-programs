package empdetails;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteEmp_Details")
public class DeleteEmp_Details extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteEmp_Details() {
		super();
	}

	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		Map<String, String[]> parameterMap = request.getParameterMap();
		String first_name = null != parameterMap.get("first_name") ? parameterMap.get("first_name")[0].toString() : "";
		String last_name = null != parameterMap.get("last_name") ? parameterMap.get("last_name")[0].toString() : "";
		String phone = null != parameterMap.get("phone") ? parameterMap.get("phone")[0].toString() : "";
		String email = null != parameterMap.get("email") ? parameterMap.get("email")[0].toString() : "";
		String city = null != parameterMap.get("city") ? parameterMap.get("city")[0].toString() : "";
		String state = null != parameterMap.get("state") ? parameterMap.get("state")[0].toString() : "";
		String pin = null != parameterMap.get("pin") ? parameterMap.get("pin")[0].toString() : "";
		String userID = null != parameterMap.get("userID") ? parameterMap.get("userID")[0].toString() : "";
		
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = openConnection();
			if (!"".equalsIgnoreCase(userID) ) {
				handleDelete(out, userID, con);
			} 
			
			displayAllUsers(out, con);
		} catch (Exception e) {
			e.printStackTrace( );
		}
	}
	
	private void displayAllUsers(PrintWriter out, Connection con) throws SQLException {
		printAddUserdetailsLink(out);

		printAllUsersTable(out, con);
	}
	
	private void printAddUserdetailsLink(PrintWriter out) {
		out.append("<p align='center'><table name ='addUserTable'>");
		out.append("<tr>");
		out.append("<td><a href='/Test2/emp_details.html'>Add User</a></td>");
		out.append("</tr>");
		out.print("</table></p>");
	}

	private void handleDelete(PrintWriter out, String userID, Connection con) throws SQLException {
		ResultSet rs = loadUser(userID, con);
		rs.next();

		deleteUser(userID, con);

		printUserDeleteStatus(out, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
	}
	private void printUserDeleteStatus(PrintWriter out, String first_name, String last_name, String phone, String email, String city, String state, String pin) {
		out.append("<p align=center><b>User Deleted success: </b></p>");
		out.append("</br> first_name :" + first_name);
		out.append("&nbsp;&nbsp;&nbsp; last_name: " + last_name);
		out.append("&nbsp;&nbsp;&nbsp; phone :" + phone);
		out.append("&nbsp;&nbsp;&nbsp; email :" + email);
		out.append("&nbsp;&nbsp;&nbsp; city :" + city);
		out.append("&nbsp;&nbsp;&nbsp; state :" + state);
		out.append("&nbsp;&nbsp;&nbsp; pin :" + pin);
	}
	private Connection openConnection() throws ClassNotFoundException, SQLException {
		Connection con;
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/anil", "root", "root");
		System.out.println("Employee Deleted...!");
		return con;
	}	
	private void printAllUsersTable(PrintWriter out, Connection con) throws SQLException {
		ResultSet rs = loadAllUsers(con);
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();

		out.append("<p align='center'><table border=2>");
		out.append("<tr>");
		for (int i = 0; i < colCount; i++) {
			out.append("<th>" + rsmd.getColumnLabel(i + 1) + "</th>");
		}
		out.append("</tr>");
		while (rs.next()) {
			out.append("<tr>");
			String first_name = rs.getString(1);
			for (int i = 1; i <= colCount; i++) {
				out.append("<td>" + rs.getString(i) + "</td>");
			}
			createEditbutton(out, first_name);
			createDeletebutton(out, first_name);

			out.append("</tr>");
		}
		out.print("</table></p>");
	}

	private ResultSet loadAllUsers(Connection con) throws SQLException {
		String qry = "SELECT * FROM anil.emp_details";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(qry);
		return rs;
	}


	private void createDeletebutton(PrintWriter out, String first_name) {
		out.append(
				"<td>" + "<form action='/Test2/DeleteEmp_Details' method=post>" + "<input type='hidden' name='userID' value='"
						+ first_name + "'/>" 
						+ "<input type=submit value='Delete'/>" + "</form>" + "</td>");
	}

	private void createEditbutton(PrintWriter out, String first_name) {
		out.append("<td>" + "<form action='/Test2/EditEmp_Details' method=post>"
				+ "<input type='hidden' name='userID' value='" + first_name + "'/>" + "<input type=submit value='Edit' />"
				+ "</form>" + "</td>");
	}
	private int deleteUser(String userID, Connection con) throws SQLException {
		String qry = "Delete FROM anil.emp_details where first_name=?";
		PreparedStatement ps = con.prepareStatement(qry);
		ps.setString(1, userID);
		int deletedRowCount = ps.executeUpdate();
		return deletedRowCount;
	}

	private ResultSet loadUser(String userID, Connection con) throws SQLException {
		String qry = "SELECT * FROM anil.emp_details where first_name=?";
		PreparedStatement ps = con.prepareStatement(qry);
		ps.setString(1, userID);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
