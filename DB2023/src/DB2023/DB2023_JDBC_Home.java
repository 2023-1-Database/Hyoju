package DB2023;
import java.sql.*;

public class DB2023_JDBC_Home {
	
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/DB2023Team04";
	static final String USER = "root";
	static final String PASS = "1234";
	
	//String userid, String passwd
	public static void DB2023_JDBC_search(String target, String input) {
		try{
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			
			//°Ë»ö ±â´É
			PreparedStatement psSearch_A = conn.prepareStatement("select * from DB2023_Book_result where " + target + " like ?;");
			psSearch_A.setString(1, "%"+input+"%");
			
			ResultSet rSet = psSearch_A.executeQuery();
			while(rSet.next()) {
				System.out.println(rSet.getString("Call_num")+" "+rSet.getString("Author")+" "+ rSet.getString("Book_Title"));
			}
		}
		catch(SQLException sqle) {
			System.out.println("SQLException:" + sqle);
		}
	}
	
}
