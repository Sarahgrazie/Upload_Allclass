package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
* Database Connection
* creation
* close
*/
public class DbConnection {
//	Db연결 + 연결 종료에 사용되는 Class
//	JDBC를 사용 + oracle db 연동? 
	static final String driver = "oracle.jdbc.driver.OracleDriver";
	static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	static final String userid = "ora_user";
	static final String password = "1234";
	
	private DbConnection() { // new 못한다.
	}
	
	public static Connection getConnection() {
//		getConnection 이 메서드는 꺼내는것! / 연결된 connection object 반환 
		Connection conn = null;
		try {
			Class.forName(driver);
			// driver load하고 drivermanager통해 db연결 
			// if driver을 못찾거나 연결이 안되면 error메세지 나오게끔함 
			conn = DriverManager.getConnection(url, userid, password);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;  //error아니면 반환 
	}
	
	public static void close(Connection conn
			, PreparedStatement pstmt //db연결, pre, rs 3개 매게변수 
			, ResultSet rs) {
		try {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (conn != null) conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn, PreparedStatement pstmt) {
		//db연결 및 pres 
		// conne , pr 매게변수 
		//error 발생하는 sql excep 처리  -- 
		try {
			if (pstmt != null) pstmt.close();
			if (conn != null) conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
