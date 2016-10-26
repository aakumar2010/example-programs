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

@WebServlet("/Emp_Details")
public class Emp_Details extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Emp_Details() {
		super();
	}

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
		String action = null != parameterMap.get("action") ? parameterMap.get("action")[0].toString() : "";

		Connection con = null;
		@SuppressWarnings("unused")
		PreparedStatement ps = null;

		try {
			con = openConnection();
			if (!"".equalsIgnoreCase(userID) && "update".equalsIgnoreCase(action)) {
				hanldeUpdate(out, first_name, last_name, phone, email, city, state, pin, userID, con);
			}  else if (isValidData(first_name, last_name, phone, email, city, state, pin )) {

				handleAdd(out, first_name, last_name, phone, email, city, state, pin, con);
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

	private void handleAdd(PrintWriter out, String first_name, String last_name, String phone, String email, String city, String state, String pin,  Connection con)
			throws SQLException {
		saveUser(first_name, last_name, phone, email, city, state, pin, con);

		printUserSaveStatus(out, first_name, last_name, phone, email, city, state, pin);
	}

	private void hanldeUpdate(PrintWriter out, String first_name, String last_name, String phone, String email, String city, String state, String pin, String userID,
			Connection con) throws SQLException {
		updateUser(userID, first_name, last_name, phone, email, city, state, pin, con);

		printUserUpdateStatus(out, first_name, last_name, phone, email, city, state, pin);
	}

	private boolean isValidData(String first_name, String last_name, String phone, String email, String city, String state, String pin) {
		boolean isValid = true;
		if ("".equalsIgnoreCase(first_name))
			isValid = false;
		if ("".equalsIgnoreCase(last_name))
			isValid = false;
		if ("".equalsIgnoreCase(phone))
			isValid = false;
		if ("".equalsIgnoreCase(email))
			isValid = false;
		if ("".equalsIgnoreCase(city))
			isValid = false;
		if ("".equalsIgnoreCase(state))
			isValid = false;
		if ("".equalsIgnoreCase(pin))
			isValid = false;
		return isValid;
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

	private void createDeletebutton(PrintWriter out, String first_name) {
		out.append("<td>" + "<form action='/Test2/DeleteEmp_Details' method=post>" + "<input type='hidden' name='userID' value='"
						+ first_name + "'/>" 
						+ "<input type=submit value='Delete'/>" + "</form>" + "</td>");
	}

	private void createEditbutton(PrintWriter out, String first_name) {
		out.append("<td>" + "<form action='/Test2/EditEmp_Details' method=post>"
				+ "<input type='hidden' name='userID' value='" + first_name + "'/>" + "<input type=submit value='Edit' />"
				+ "</form>" + "</td>");
	}

	private void printAddUserdetailsLink(PrintWriter out) {
		out.append("<p align='center'><table first_name ='addUserTable'>");
		out.append("<tr>");
		out.append("<td><a href='/Test2/emp_details.html'>Add User</a></td>");
		out.append("</tr>");
		out.print("</table></p>");
	}

	private void printUserSaveStatus(PrintWriter out, String first_name, String last_name, String phone, String email, String city, String state, String pin) {
		out.append("User Saved success:");
		out.append("</br>" + first_name);
		out.append("&nbsp;&nbsp;&nbsp;" + last_name);
		out.append("&nbsp;&nbsp;&nbsp;" + phone);
		out.append("&nbsp;&nbsp;&nbsp;" + email);
		out.append("&nbsp;&nbsp;&nbsp;" + city);
		out.append("&nbsp;&nbsp;&nbsp;" + state);
		out.append("&nbsp;&nbsp;&nbsp;" + pin);
	}

	private void printUserUpdateStatus(PrintWriter out, String first_name, String last_name, String phone, String email, String city, String state, String pin) {
		out.append("User Updated success:");
		out.append("</br>" + first_name);
		out.append("&nbsp;&nbsp;&nbsp;" + last_name);
		out.append("&nbsp;&nbsp;&nbsp;" + phone);
		out.append("&nbsp;&nbsp;&nbsp;" + email);
		out.append("&nbsp;&nbsp;&nbsp;" + city);
		out.append("&nbsp;&nbsp;&nbsp;" + state);
		out.append("&nbsp;&nbsp;&nbsp;" + pin);
	}

	

	private void saveUser(String first_name, String last_name, String phone, String email, String city, String state, String pin, Connection con) throws SQLException {
		PreparedStatement ps;
		String sql = "insert into emp_details(first_name, last_name, phone, email, city, state, pin) values(?,?,?,?,?,?,?)";
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
		System.out.println("user saved!!!!!!!!!");
	}

	private void updateUser(String oldfirst_name, String first_name, String last_name, String phone, String email, String city, String state, String pin, Connection con)
			throws SQLException {
		PreparedStatement ps;
		String sql = "update anil.emp_details set first_name = ?, last_name = ?, phone = ?, email = ?, city = ?, state = ?, pin = ? where first_name = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, first_name);
		ps.setString(2, last_name);
		ps.setString(3, phone);
		ps.setString(4, email);
		ps.setString(5, city);
		ps.setString(6, state);
		ps.setString(7, pin);
		ps.setString(8, oldfirst_name);
		ps.executeUpdate();
		ps.close();
		System.out.println("user update!!!!!!!!!");
	}
	private Connection openConnection() throws ClassNotFoundException, SQLException {
		Connection con;
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/anil", "root", "root");
		System.out.println("connection success");
		return con;
	}
	private ResultSet loadAllUsers(Connection con) throws SQLException {
		String qry = "SELECT * FROM anil.emp_details";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(qry);
		return rs;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
