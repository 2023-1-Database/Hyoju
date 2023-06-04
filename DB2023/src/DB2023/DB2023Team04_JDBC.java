package DB2023;

import java.sql.*;

public class DB2023Team04_JDBC {
	
	static Connection connection;
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/DB2023Team04";
	static final String USER = "DB2023Team04";
	static final String PASS = "DB2023Team04";
	
	//데이터베이스와 연결
	public DB2023Team04_JDBC() {
		try{
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = connection.createStatement();
			System.out.println("Mysql 서버 연동 성공");			
		}
		catch(SQLException sqle) {
			System.out.println("SQLException:" + sqle);
		}
	}
	
	// 검색 기능
	public static void DB2023_JDBC_search(String target, String input) {
		
		try {
			//PreparedStatement 사용하여 검색 분야와 검색어 지정
			PreparedStatement psSearch_A = connection.prepareStatement("select * from DB2023_Book_result where " + target + " like ?;");
			psSearch_A.setString(1, "%"+input+"%");
			//결과 저장
			ResultSet rSet = psSearch_A.executeQuery();
			
			while(rSet.next()) {
				System.out.println(rSet.getString("Call_num")+" "+rSet.getString("Author")+" "+ rSet.getString("Book_Title"));
				DB2023Team04_MainFrame.BookButton(DB2023Team04_SearchResultFrame.searchbody, rSet.getString("Call_num")+" "+rSet.getString("Author")+" "+ rSet.getString("Book_Title"), rSet.getString("Book_ID"));
			}
		}
		catch (SQLException sqle) {
			System.out.println("SQLException:" + sqle);
		}
		
	}
	
}