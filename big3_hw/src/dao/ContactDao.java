package dao;
//oracle  db�� ��ȣ�ۿ��ϴ� () ����: db����, select��������, insert ��������

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import dto.ContactDto;
import util.DbConnection;

public class ContactDao { 

// ��ü ��� ��� ��ȸ	- select
// oracle db���� ��� ��� ��� �˻� 
// �� ����� ArrayList<ContactDto> ���·� ��ȯ 
	public ArrayList<ContactDto> select(){
//		����̹�, connection�� �ʿ��� ����
		String driver 	= "oracle.jdbc.driver.OracleDriver";
		String url 		= "jdbc:oracle:thin:@localhost:1521:xe";
		String user 	= "ora_user";
		String password = "1234";
		
//		Connection, PraparedStatement, ResultSet(select) - db�� ����? 
		Connection conn 		= null;
		PreparedStatement pstmt = null;
		ResultSet rs 			= null; // select 
		
//		sql => select��
//		select������ �̿��Ͽ� emp,dept table join 
//		��� ����� empno, empnm,deptnm�� �˻��� 
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT e.EMPNO				");
		sql.append("	 , e.EMPNM				");
		sql.append("     , d.DEPTNM				");
		sql.append("  FROM EMP e				");
		sql.append("     , DEPT d				");
		sql.append(" WHERE e.DEPTNO = d.DEPTNO	");
		
		ArrayList<ContactDto> aList = new ArrayList<>(); //�����ȯ? 
		
		try {
//		1. driver load
			Class.forName(driver);
//		2. Connection ����
			conn = DriverManager.getConnection(url, user, password);
//		3. PreparedStatement ����
			pstmt = conn.prepareStatement(sql.toString()); // parameter : String
//		4. ���ε� ���� : ? ���� => ����
			rs = pstmt.executeQuery(); // select ����, return ResultSet
//		5. ContactDto => ArrayList add
			while(rs.next()) { // rs.next() : �� Row ó�� : �Ѹ��� ���� => DTO
				ContactDto cdto = new ContactDto();
				cdto.setEmpNo(rs.getString("empno"));
				cdto.setEmpNm(rs.getString("empnm"));
				cdto.setDeptNm(rs.getString("deptnm"));
				aList.add(cdto);
				
			}
		}catch(ClassNotFoundException e) {
			System.out.println(e.getMessage()); // ����̹� Ŭ������ ���� ���
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return aList;
	} // select end
	
//	����� �˻� -> ������ -> return ArrayList<ContactDto>
	public ArrayList<ContactDto> select(String empnm){ //select �޼��� 
//		�� �޼���� ������� ������� oracle db���� ����� �˻��ϰ�
//		�� ����� ArrayList<ContactDto> �� ��ȯ 
//		�����Բ��� �Է����ֽ� ������� ������� like�����ڸ� ����Ͽ� ��ġ�ϴ� ����� �˻���.
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String userid = "ora_user";
		String password = "1234";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT e.EMPNO					");
		sql.append(", e.EMPNM						");
		sql.append(", d.DEPTNM						");
		sql.append("FROM EMP e						");
		sql.append(", DEPT d						");
		sql.append("WHERE e.DEPTNO = d.DEPTNO		");
		sql.append("AND e.EMPNM LIKE '%' || ? || '%'");
		
		ArrayList<ContactDto> aList = new ArrayList<ContactDto>();
//		Database ���� �ڵ�
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, password);
			pstmt = conn.prepareStatement(sql.toString());
//			? �Ѱ� �� <= �̸��� ����
			pstmt.setString(1, empnm); // parameter empnm setting
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ContactDto cdto = new ContactDto();
				cdto.setEmpNo(rs.getString("empno"));
				cdto.setEmpNm(rs.getString("empnm"));
				cdto.setDeptNm(rs.getString("deptnm"));
				
				aList.add(cdto);
			}
		}catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace(); // ���� ���� ���
		}finally {
			try {
				if(rs != null)rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return aList;
	}
	
//	Ư�� �μ��� �Ҽӵ� ��� ��ȸ => �μ��� ��ȸ
//	String deptnm �� �޼���� Ư�� �μ����� ������� oracle db���� �ش� �μ��� �Ҽӵ� ����� 
//	�˻��ϰ� �� ����� ArrayList<ContactDto>�� ��ȯ 
	public ArrayList<ContactDto> selectByDeptNm(String deptnm){
		
		Connection conn 		= DbConnection.getConnection();
//		�ٷ� �� ������ �޼��带 ����Ͽ� db�� �����ϰ�, �μ����� ������� sql Ŀ�� ����. 
		PreparedStatement pstmt = null;
		ResultSet rs 			= null;
		
		StringBuilder sql = new StringBuilder();  //sql ���� �ۼ� 
		sql.append("SELECT e.EMPNO							");
		sql.append("	 , e.EMPNM							");
		sql.append("	 , e.DEPTNO							");
		sql.append("	 , e.SALARY							");
		sql.append("	 , d.DEPTNM							");
		sql.append("  FROM emp e							");
		sql.append("	 , dept d							");
		sql.append(" WHERE e.DEPTNO = d.DEPTNO				");
		sql.append("   AND d.DEPTNM like '%' || ? || '%'	");
		
		ArrayList<ContactDto> aList = new ArrayList<ContactDto>();
		
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, deptnm); // �μ��� ����
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ContactDto cdto = new ContactDto();
				cdto.setEmpNo(rs.getString("empno"));
				cdto.setEmpNm(rs.getString("empnm"));
				cdto.setDeptNo(rs.getString("deptno"));
				cdto.setDeptNm(rs.getString("deptnm"));
				cdto.setSalary(rs.getInt("salary"));
				
				aList.add(cdto);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DbConnection.close(conn, pstmt, rs);
		}
		
		return aList;
	}
// 	�߰� INSERT ���� 
	// ContactDao.java

	// ���ο� ����ó ������ �߰��ϴ� INSERT ����
	public void insertContact(String empNo, String empNm, String deptNo, int salary) {
	    Connection conn = DbConnection.getConnection();
	    PreparedStatement pstmt = null;
	    
	    String sql = "INSERT INTO EMP (EMPNO, EMPNM, DEPTNO, SALARY) VALUES (?, ?, ?, ?)";
	    
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, empNo);
	        pstmt.setString(2, empNm);
	        pstmt.setString(3, deptNo);
	        pstmt.setInt(4, salary);
	        
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DbConnection.close(conn, pstmt, null);
	    }
	}
	// ���ο� ����ó ������ �����ϴ� delete ����
    public void deleteContact(String empNo) {
        Connection conn = DbConnection.getConnection();
        String deleteQuery = "DELETE FROM CONTACT WHERE EMPNO = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
            pstmt.setString(1, empNo);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	// ���ο� ����ó ������ �߰��ϴ� update ����
    public void updateContact(String empNo, String newDeptNm) {
        Connection conn = DbConnection.getConnection();
        String updateQuery = "UPDATE CONTACT SET DEPTNM = ? WHERE EMPNO = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
            pstmt.setString(1, newDeptNm);
            pstmt.setString(2, empNo);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}







