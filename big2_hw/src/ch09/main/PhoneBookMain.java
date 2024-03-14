package ch09.main;

import java.lang.reflect.Member;
import java.nio.channels.MembershipKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import ch09.lib.PhoneBook;

public class PhoneBookMain {
	
	private static void showMenu(){
		System.out.println("===========");
		System.out.println("회원관리 프로그램");
		System.out.println("1. 회원추가");
		System.out.println("2. 회원목록");
		System.out.println("3. 회원수정");
		System.out.println("4. 회원삭제");
		System.out.println("5. 종료");
		System.out.println("번호 입력:");
	}
	static void memberInsert(
			HashMap<String, PhoneBook> members
			,Scanner scan) {
		PhoneBook member = new PhoneBook();
		System.out.println("이름 입력");
		member.setName(scan.next());
		System.out.println("주소");
		member.setAddress(scan.next());
		System.out.println("전화번호 입력");
		member.setPhoneNumber(scan.next());
		System.out.println("그룹 입력");
		member.setGroup(scan.next());
		
		
		System.out.println(member.toString());
	}
	
	static void memberSelectAll(HashMap<String, PhoneBook> members){
		Set<String>keys = members.keySet();		
		Iterator<String> it = keys.iterator();
		while(it.hasNext()) {
			String key = it.next();
			System.out.println(members.get(key));
		}
	}
	
	static void memberModify(
		HashMap<String, PhoneBook> members, Scanner scan){
			PhoneBook member = new PhoneBook();
			System.out.println("==========");
			System.out.println("회  원  수 정");
			System.out.println("==========");
			System.out.println("수정할 회원의 전화번호 입력: ");
			String PhoneNumberToModify= scan.next();
			
			
			if(members.containsKey(PhoneNumberToModify)){
			HashMap<String, PhoneBook> Members;
			PhoneBook memberToModify = Members.get(memberToModify);
			System.out.println("==========");
			System.out.println("현재 회원 정보");
			System.out.println(memberToModify);
			
			System.out.println("새로운 이름 입력(변경하지 않으려면 Enter");
			String newName = scan.next();
			
			if(!newName.isEmpty()){
				memberToModify.setName(newName);
			}
			System.out.println("새로운 주소 입력(변경하지 않으려면 Enter");
			String newAddress  = Scanner.nextLine();   //왜 에러가 날까? 
		
			
			if(!newAddress.isEmpty()){
				memberToModify.setAddress(newAddress);
			}
			System.out.println("새로운 그룹 입력(변경하지 않으려면 Enter)");
			String newGroup = scan.next();

			if(!newGroup.isBlank()){
				memberToModify.setGroup(newGroup);
			}
			System.out.println("회원 정보가 업데이트 되었습니다");
			System.out.println(memberToModify);
			}else {
				System.out.println("해당 전화번호로 등록된 회원이 없습니다.");
			}
		}
			
		static void memberDelete(HashMap<String, PhoneBook> members, 
				Scanner scan){
			System.out.println("=========");
			System.out.println("회 원 삭 제");
			System.out.println("삭제할 회원의 전화번호 입력: ");
			String phoneNumberToDelete = scan.next();
			
			if(members.containsKey(phoneNumberToDelete)){
				PhoneBook memberToDelete = members.get(phoneNumberToDelete);
				System.out.println("=======");
				System.out.println(memberToDelete);
				System.out.println("정말로 삭제하시겠습니까? (Y/N): ");
				String confirmation = scan.next().toUpperCase();
				if(confirmation.equals("Y")){
					members.remove(phoneNumberToDelete);
					}else{
						System.out.println("삭제가 취소되었습니다.");
					}
			}else {
				System.out.println("해당 전화번호로 등록된 회원이 없습니다.");
			}
			
		}
		public static void main(String[] args) {
			HashMap<String, PhoneBook> members =
					new HashMap<String, PhoneBook>();
			
			Scanner scan = new Scanner(System.in);
			
		
			while(true){
				String menuNumber=scan.next();
				showMenu();
				System.out.println("선택한 번호: "+ menuNumber);
				if(menuNumber.equals("1")) {
					memberInsert(members,scan);
				}
				if(menuNumber.equals("2")){
					memberSelectAll(members);
				}
				if(menuNumber.equals("3")){
					memberModify(members, scan);
					
				if(menuNumber.equals("4")){
					memberDelete(members, scan);	
				}
				if(menuNumber.equals("5")){
					System.out.println("종료됩니다.");
					break;
					}		
				}
				scan.close();
			}
			
		}
	}
