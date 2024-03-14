package dto;

// select한 결과를 저장하는 클래스
public class ContactDto {
	private String empNo;
	private String empNm;
	private String deptNm;
	private String deptNo;
	private int salary;
	
	
	public ContactDto() {
		
	}
	
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getEmpNm() {
		return empNm;
	}
	public void setEmpNm(String empNm) {
		this.empNm = empNm;
	}
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	public String getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	@Override
	public String toString() {
		return "사원번호=" + empNo + ", 사원명=" + empNm + ", 부서명=" + deptNm + 
				", 부서번호=" + deptNo + ", 급여=" + salary + "]";
	}
	
}
	
	