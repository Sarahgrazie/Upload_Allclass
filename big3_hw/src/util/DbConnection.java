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
//	Db���� + ���� ���ῡ ���Ǵ� Class
//	JDBC�� ��� + oracle db ����? 
	static final String driver = "oracle.jdbc.driver.OracleDriver";
	static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	static final String userid = "ora_user";
	static final String password = "1234";
	
	private DbConnection() { // new ���Ѵ�.
	}
	
	public static Connection getConnection() {
//		getConnection �� �޼���� �����°�! / ����� connection object ��ȯ 
		Connection conn = null;
		try {
			Class.forName(driver);
			// driver load�ϰ� drivermanager���� db���� 
			// if driver�� ��ã�ų� ������ �ȵǸ� error�޼��� �����Բ��� 
			conn = DriverManager.getConnection(url, userid, password);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;  //error�ƴϸ� ��ȯ 
	}
	
	public static void close(Connection conn
			, PreparedStatement pstmt //db����, pre, rs 3�� �ŰԺ��� 
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
		//db���� �� pres 
		// conne , pr �ŰԺ��� 
		//error �߻��ϴ� sql excep ó��  -- 
		try {
			if (pstmt != null) pstmt.close();
			if (conn != null) conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
