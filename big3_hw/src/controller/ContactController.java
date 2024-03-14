package controller; //ȸ������ ���α׷��� �ֿ� controller�� ����/ db ���� 

import java.util.ArrayList;

import dao.ContactDao;
import dto.ContactDto;

public class ContactController {

//	��ü �޴� ��� => View ���� ó�� �ʿ�
//	showMenu()�� �޴��� ȭ�鿡 ����ϴ� ������ �Ѵ�.
	static void showMenu() {
		System.out.println("====================");
		System.out.println("    ȸ���������α׷�   ");
		System.out.println("====================");
		System.out.println("1. ȸ�� �߰�");
		System.out.println("2. ȸ�� ���");
		System.out.println("3. ȸ�� ����");
		System.out.println("4. ȸ�� ����");
		System.out.println("5. ����");
		System.out.print("��ȣ �Է� : ");
	}
	/*
	 * ��ûó��
	 * 1. ȸ���߰�, 2.ȸ����� 3.ȸ������ 4.ȸ������ 5.����
	 * �����ͺ��̽� ���� ���߼���
	 * 1. ������ ����, �׽�Ʈ ����(����� ���)
	 * 2. DAO ���� 
	 * 3. Service ����
	 * 4. Controller ����
	 * 5. View ����
	 */
	public static void main(String[] args) { //���α׷��� ����? 
		ContactDao cDao = new ContactDao();//ContactDao object�� �����Ͽ� DB����
		
//		��ü��� ��� ����
		ArrayList<ContactDto> cList = cDao.select();
//		SELECT �޼��� ȣ���Ͽ� DB���� ��ü ������ ���� 
//		����� ��������� cList �� �����ϰ� �ݺ����� ����Ͽ� �����. 
		for(int i=0;i<cList.size();i++) {
			System.out.println(cList.get(i));
		}
		
//		����� ��ȸ ����
		ArrayList<ContactDto> cList1 = cDao.select("��");
//		select �޼��� ȣ���Ͽ� "��"�� ���Ե� ������� ���� 
//		����� ��������� cList1�� �����ϰ�, �ݺ����� ����Ͽ� �����. 
		for(int i=0;i<cList1.size();i++) {
			System.out.println(cList1.get(i));
		}
		
//		�μ��� ��ȸ ����
//		����� ��ȸ ����
		ArrayList<ContactDto> cList2 = cDao.selectByDeptNm("��");
		for(int i=0;i<cList2.size();i++) {
			System.out.println(cList2.get(i));
		}
		//����� ���� 
		
		//��� ���� 
		
		//���� 
		
	}

}
