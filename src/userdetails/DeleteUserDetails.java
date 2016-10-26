package userdetails;
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

@WebServlet("/DeleteUserDetails")
public class DeleteUserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteUserDetails() {
		super();
	}

	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		Map<String, String[]> parameterMap = request.getParameterMap();
		String userName = null != parameterMap.get("UserName") ? parameterMap.get("UserName")[0].toString() : "";
		String userEmail = null != parameterMap.get("UserEmail") ? parameterMap.get("UserEmail")[0].toString() : "";
		String userPhone = null != parameterMap.get("UserPhone") ? parameterMap.get("UserPhone")[0].toString() : "";
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
		out.append("<td><a href='/Test2/adduserdetails.html'>Add User</a></td>");
		out.append("</tr>");
		out.print("</table></p>");
	}

	private void handleDelete(PrintWriter out, String userID, Connection con) throws SQLException {
		ResultSet rs = loadUser(userID, con);
		rs.next();

		deleteUser(userID, con);

		printUserDeleteStatus(out, rs.getString(1), rs.getString(2), rs.getString(3));
	}
	private void printUserDeleteStatus(PrintWriter out, String userName, String userEmail, String userPhone) {
		out.append("<p align=center><b>User Deleted success: </b></p>");
		out.append("</br> userName :" + userName);
		out.append("&nbsp;&nbsp;&nbsp; userEmail: " + userEmail);
		out.append("&nbsp;&nbsp;&nbsp; userPhone :" + userPhone);
	}
	private Connection openConnection() throws ClassNotFoundException, SQLException {
		Connection con;
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/anil", "root", "root");
		System.out.println("connection success");
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
			String userName = rs.getString(1);
			for (int i = 1; i <= colCount; i++) {
				out.append("<td>" + rs.getString(i) + "</td>");
			}
			createEditbutton(out, userName);
			createDeletebutton(out, userName);

			out.append("</tr>");
		}
		out.print("</table></p>");
	}

	private ResultSet loadAllUsers(Connection con) throws SQLException {
		String qry = "SELECT * FROM anil.user_details";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(qry);
		return rs;
	}


	private void createDeletebutton(PrintWriter out, String userName) {
		out.append(
				"<td>" + "<form action='/Test2/DeleteUserDetails' method=post>" + "<input type='hidden' name='userID' value='"
						+ userName + "'/>" 
						+ "<input type=submit value='Delete'/>" + "</form>" + "</td>");
	}

	private void createEditbutton(PrintWriter out, String userName) {
		out.append("<td>" + "<form action='/Test2/EditUserDetails' method=post>"
				+ "<input type='hidden' name='userID' value='" + userName + "'/>" + "<input type=submit value='Edit' />"
				+ "</form>" + "</td>");
	}
	private int deleteUser(String userID, Connection con) throws SQLException {
		String qry = "Delete FROM anil.user_details where UserName=?";
		PreparedStatement ps = con.prepareStatement(qry);
		ps.setString(1, userID);
		int deletedRowCount = ps.executeUpdate();
		return deletedRowCount;
	}

	private ResultSet loadUser(String userID, Connection con) throws SQLException {
		String qry = "SELECT * FROM anil.user_details where UserName=?";
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
