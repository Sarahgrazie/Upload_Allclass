package ch09.lib;

public class PhoneBook {
	private String name;
	private String address;
	private String phoneNumber;
	private String group;
			
	public PhoneBook(){		
	}
	
	public PhoneBook(String name
					,String address
					,String phoneNumber
					,String group){
		
		this.name=name;
		this.address=address;
		this.phoneNumber=phoneNumber;
		this.group=group;
	}
	@Override
	public String toString() {
		return "PhoneBook [name=" + name + 
				", address=" + address + ", phoneNumber=" 
				+ phoneNumber + ", group=" + group
				+ "]";
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
}	
	
