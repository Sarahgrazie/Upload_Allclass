package controller; //회원관리 프로그램을 주요 controller가 역할/ db 연동 

import java.util.ArrayList;

import dao.ContactDao;
import dto.ContactDto;

public class ContactController {

//	전체 메뉴 출력 => View 에서 처리 필요
//	showMenu()는 메뉴를 화면에 출력하는 역할을 한다.
	static void showMenu() {
		System.out.println("====================");
		System.out.println("    회원관리프로그램   ");
		System.out.println("====================");
		System.out.println("1. 회원 추가");
		System.out.println("2. 회원 목록");
		System.out.println("3. 회원 수정");
		System.out.println("4. 회원 삭제");
		System.out.println("5. 종료");
		System.out.print("번호 입력 : ");
	}
	/*
	 * 요청처리
	 * 1. 회원추가, 2.회원목록 3.회원수정 4.회원삭제 5.종료
	 * 데이터베이스 연동 개발순서
	 * 1. 쿼리를 실행, 테스트 진행(디비툴 사용)
	 * 2. DAO 개발 
	 * 3. Service 개발
	 * 4. Controller 개발
	 * 5. View 개발
	 */
	public static void main(String[] args) { //프로그램의 시작? 
		ContactDao cDao = new ContactDao();//ContactDao object를 생성하여 DB연동
		
//		전체사원 목록 추출
		ArrayList<ContactDto> cList = cDao.select();
//		SELECT 메서드 호출하여 DB에서 전체 사원목록 추출 
//		추출된 사원정보는 cList 에 저장하고 반복문을 사용하여 출력함. 
		for(int i=0;i<cList.size();i++) {
			System.out.println(cList.get(i));
		}
		
//		사원명 조회 추출
		ArrayList<ContactDto> cList1 = cDao.select("고");
//		select 메서드 호출하여 "고"가 포함된 사원정보 추출 
//		추출된 사원정보는 cList1에 저장하고, 반복문을 사용하여 출력함. 
		for(int i=0;i<cList1.size();i++) {
			System.out.println(cList1.get(i));
		}
		
//		부서명 조회 추출
//		사원명 조회 추출
		ArrayList<ContactDto> cList2 = cDao.selectByDeptNm("발");
		for(int i=0;i<cList2.size();i++) {
			System.out.println(cList2.get(i));
		}
		//사원명 수정 
		
		//사원 삭제 
		
		//종료 
		
	}

}
