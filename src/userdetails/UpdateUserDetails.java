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


@WebServlet("/UpdateUserDetails")
public class UpdateUserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public UpdateUserDetails() {
        super();
        
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		Map<String, String[]> parameterMap = request.getParameterMap();
		String userName = null != parameterMap.get("UserName") ? parameterMap.get("UserName")[0].toString() : "";
		String userEmail = null != parameterMap.get("UserEmail") ? parameterMap.get("UserEmail")[0].toString() : "";
		String userPhone = null != parameterMap.get("UserPhone") ? parameterMap.get("UserPhone")[0].toString() : "";
		String userID = null != parameterMap.get("userID") ? parameterMap.get("userID")[0].toString() : "";
		@SuppressWarnings("unused")
		String action = null != parameterMap.get("action") ? parameterMap.get("action")[0].toString() : "";

		Connection con = null;
		@SuppressWarnings("unused")
		PreparedStatement ps = null;

		try {
			con = openConnection();
			if (!"".equalsIgnoreCase(userID)) {
				hanldeUpdate(out, userName, userEmail, userPhone, userID, con);
			}  

			displayAllUsers(out, con);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void displayAllUsers(PrintWriter out, Connection con) throws SQLException {
		printAddUserdetailsLink(out);

		printAllUsersTable(out, con);
	}
	private void hanldeUpdate(PrintWriter out, String userName, String userEmail, String userPhone, String userID,
			Connection con) throws SQLException {
		updateUser(userID, userName, userEmail, userPhone, con);

		printUserUpdateStatus(out, userName, userEmail, userPhone);
	}
	private void printAddUserdetailsLink(PrintWriter out) {
		out.append("<p align='center'><table name ='addUserTable'>");
		out.append("<tr>");
		out.append("<td><a href='/Test2/adduserdetails.html'>Add User</a></td>");
		out.append("</tr>");
		out.print("</table></p>");
	}
	private void updateUser(String oldUserName, String userName, String userEmail, String userPhone, Connection con)
			throws SQLException {
		PreparedStatement ps;
		String sql = "update anil.user_details set UserName = ?, UserEmail = ?, UserPhone = ? where UserName = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, userName);
		ps.setString(2, userEmail);
		ps.setString(3, userPhone);
		ps.setString(4, oldUserName);
		ps.executeUpdate();
		ps.close();
		System.out.println("user update!!!!!!!!!");
	}
	private void printUserUpdateStatus(PrintWriter out, String userName, String userEmail, String userPhone) {
		out.append("User Updated success:");
		out.append("</br>" + userName);
		out.append("&nbsp;&nbsp;&nbsp;" + userEmail);
		out.append("&nbsp;&nbsp;&nbsp;" + userPhone);
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
			@SuppressWarnings("unused")
			String userName = rs.getString(1);
			for (int i = 1; i <= colCount; i++) {
				out.append("<td>" + rs.getString(i) + "</td>");
			}
			//createEditbutton(out, userName);
			//createDeletebutton(out, userName);

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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
