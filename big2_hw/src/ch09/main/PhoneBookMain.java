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
		System.out.println("ȸ������ ���α׷�");
		System.out.println("1. ȸ���߰�");
		System.out.println("2. ȸ�����");
		System.out.println("3. ȸ������");
		System.out.println("4. ȸ������");
		System.out.println("5. ����");
		System.out.println("��ȣ �Է�:");
	}
	static void memberInsert(
			HashMap<String, PhoneBook> members
			,Scanner scan) {
		PhoneBook member = new PhoneBook();
		System.out.println("�̸� �Է�");
		member.setName(scan.next());
		System.out.println("�ּ�");
		member.setAddress(scan.next());
		System.out.println("��ȭ��ȣ �Է�");
		member.setPhoneNumber(scan.next());
		System.out.println("�׷� �Է�");
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
			System.out.println("ȸ  ��  �� ��");
			System.out.println("==========");
			System.out.println("������ ȸ���� ��ȭ��ȣ �Է�: ");
			String PhoneNumberToModify= scan.next();
			
			
			if(members.containsKey(PhoneNumberToModify)){
			HashMap<String, PhoneBook> Members;
			PhoneBook memberToModify = Members.get(memberToModify);
			System.out.println("==========");
			System.out.println("���� ȸ�� ����");
			System.out.println(memberToModify);
			
			System.out.println("���ο� �̸� �Է�(�������� �������� Enter");
			String newName = scan.next();
			
			if(!newName.isEmpty()){
				memberToModify.setName(newName);
			}
			System.out.println("���ο� �ּ� �Է�(�������� �������� Enter");
			String newAddress  = Scanner.nextLine();   //�� ������ ����? 
		
			
			if(!newAddress.isEmpty()){
				memberToModify.setAddress(newAddress);
			}
			System.out.println("���ο� �׷� �Է�(�������� �������� Enter)");
			String newGroup = scan.next();

			if(!newGroup.isBlank()){
				memberToModify.setGroup(newGroup);
			}
			System.out.println("ȸ�� ������ ������Ʈ �Ǿ����ϴ�");
			System.out.println(memberToModify);
			}else {
				System.out.println("�ش� ��ȭ��ȣ�� ��ϵ� ȸ���� �����ϴ�.");
			}
		}
			
		static void memberDelete(HashMap<String, PhoneBook> members, 
				Scanner scan){
			System.out.println("=========");
			System.out.println("ȸ �� �� ��");
			System.out.println("������ ȸ���� ��ȭ��ȣ �Է�: ");
			String phoneNumberToDelete = scan.next();
			
			if(members.containsKey(phoneNumberToDelete)){
				PhoneBook memberToDelete = members.get(phoneNumberToDelete);
				System.out.println("=======");
				System.out.println(memberToDelete);
				System.out.println("������ �����Ͻðڽ��ϱ�? (Y/N): ");
				String confirmation = scan.next().toUpperCase();
				if(confirmation.equals("Y")){
					members.remove(phoneNumberToDelete);
					}else{
						System.out.println("������ ��ҵǾ����ϴ�.");
					}
			}else {
				System.out.println("�ش� ��ȭ��ȣ�� ��ϵ� ȸ���� �����ϴ�.");
			}
			
		}
		public static void main(String[] args) {
			HashMap<String, PhoneBook> members =
					new HashMap<String, PhoneBook>();
			
			Scanner scan = new Scanner(System.in);
			
		
			while(true){
				String menuNumber=scan.next();
				showMenu();
				System.out.println("������ ��ȣ: "+ menuNumber);
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
					System.out.println("����˴ϴ�.");
					break;
					}		
				}
				scan.close();
			}
			
		}
	}
