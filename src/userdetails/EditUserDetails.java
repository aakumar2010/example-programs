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

@WebServlet("/EditUserDetails")
public class EditUserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditUserDetails() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		Map<String, String[]> parameterMap = request.getParameterMap();
		String userName = parameterMap.get("userID")[0].toString();
		try {
			Connection con = null;
			con = openConnection();
			
			ResultSet rs = loadUser(userName, con);
			rs.next();
			out.append("<p align=center>Update User: </p>");
			out.append("</br>" );
			out.append("<form name='Per' action='/Test2/UserDetails' method='post'>"
					+ "<table>"
					+ "<tbody>"
					+ "<tr><td>UserName: </td>"
					+ "<td><input type='text' name='UserName' value='"+ rs.getString(1)+"' />"
					+ "</tr><tr><td>UserEmail: </td>"
					+ "<td><input type='text' name='UserEmail' value='"+ rs.getString(2)+"' />"
					+ "</tr>"
					+ "<tr><td>UserPhone: </td>"
					+ "<td><input type='text' name='UserPhone' value='"+ rs.getString(3)+"' />"
					+ "</tr><tr><td>&nbsp;</td>"
					+ "<td>"
					+ "<input type='hidden' name='userID' value='"+ rs.getString(1) +"'/>"
					+ "<input type='hidden' name='action' value='update'/>"
					+ "<input type='submit' name='Submit'/>"
					+ "</tr></tbody>"
					+ "</table>");

			
			//printEditUserdetailsLink(out,rs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private ResultSet loadUser(String userID,Connection con) throws SQLException {
		String qry = "SELECT * FROM anil.user_details where UserName=?";
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
