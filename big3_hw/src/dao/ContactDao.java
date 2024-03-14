package dao;
//oracle  db와 상호작용하는 () 정의: db연결, select쿼리실행, insert 쿼리실행

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import dto.ContactDto;
import util.DbConnection;

public class ContactDao { 

// 전체 사원 목록 조회	- select
// oracle db에서 모든 사원 목록 검색 
// 그 결과를 ArrayList<ContactDto> 형태로 반환 
	public ArrayList<ContactDto> select(){
//		드라이버, connection에 필요한 변수
		String driver 	= "oracle.jdbc.driver.OracleDriver";
		String url 		= "jdbc:oracle:thin:@localhost:1521:xe";
		String user 	= "ora_user";
		String password = "1234";
		
//		Connection, PraparedStatement, ResultSet(select) - db와 연결? 
		Connection conn 		= null;
		PreparedStatement pstmt = null;
		ResultSet rs 			= null; // select 
		
//		sql => select문
//		select쿼리를 이용하여 emp,dept table join 
//		모든 사원의 empno, empnm,deptnm을 검색함 
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT e.EMPNO				");
		sql.append("	 , e.EMPNM				");
		sql.append("     , d.DEPTNM				");
		sql.append("  FROM EMP e				");
		sql.append("     , DEPT d				");
		sql.append(" WHERE e.DEPTNO = d.DEPTNO	");
		
		ArrayList<ContactDto> aList = new ArrayList<>(); //결과반환? 
		
		try {
//		1. driver load
			Class.forName(driver);
//		2. Connection 생성
			conn = DriverManager.getConnection(url, user, password);
//		3. PreparedStatement 생성
			pstmt = conn.prepareStatement(sql.toString()); // parameter : String
//		4. 바인드 변수 : ? 없다 => 실행
			rs = pstmt.executeQuery(); // select 실행, return ResultSet
//		5. ContactDto => ArrayList add
			while(rs.next()) { // rs.next() : 한 Row 처리 : 한명사원 정보 => DTO
				ContactDto cdto = new ContactDto();
				cdto.setEmpNo(rs.getString("empno"));
				cdto.setEmpNm(rs.getString("empnm"));
				cdto.setDeptNm(rs.getString("deptnm"));
				aList.add(cdto);
				
			}
		}catch(ClassNotFoundException e) {
			System.out.println(e.getMessage()); // 드라이버 클래스가 없을 경우
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
	
//	사원명 검색 -> 여러명 -> return ArrayList<ContactDto>
	public ArrayList<ContactDto> select(String empnm){ //select 메서드 
//		이 메서드는 사원명을 기반으로 oracle db에서 사원을 검색하고
//		그 결과를 ArrayList<ContactDto> 로 반환 
//		선생님께서 입력해주신 사원명을 기반으로 like연산자를 사용하여 일치하는 사원을 검색함.
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
//		Database 연동 코드
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, password);
			pstmt = conn.prepareStatement(sql.toString());
//			? 한개 값 <= 이름을 세팅
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
			e.printStackTrace(); // 에러 추적 출력
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
	
//	특정 부서에 소속된 사원 조회 => 부서명 조회
//	String deptnm 이 메서드는 특정 부서명을 기반으로 oracle db에서 해당 부서에 소속된 사원을 
//	검색하고 그 결과를 ArrayList<ContactDto>로 반환 
	public ArrayList<ContactDto> selectByDeptNm(String deptnm){
		
		Connection conn 		= DbConnection.getConnection();
//		바로 위 오른쪽 메서드를 사용하여 db를 연결하고, 부서명을 기반으로 sql 커리 실행. 
		PreparedStatement pstmt = null;
		ResultSet rs 			= null;
		
		StringBuilder sql = new StringBuilder();  //sql 구문 작성 
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
			pstmt.setString(1, deptnm); // 부서명 세팅
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
// 	추가 INSERT 쿼리 
	// ContactDao.java

	// 새로운 연락처 정보를 추가하는 INSERT 쿼리
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
	// 새로운 연락처 정보를 삭제하는 delete 쿼리
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
	// 새로운 연락처 정보를 추가하는 update 쿼리
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







