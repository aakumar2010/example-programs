package org.anil.bean;

public class Employee {

	int id;
	String Name;
	String Email;
	int Phone;

	public Employee() {
		super();
	}

	public Employee(int i, String Name, String Email, int Phone) {
		super();
		this.id = i;
		this.Name = Name;
		this.Email = Email;
		this.Phone = Phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public int getPhone() {
		return Phone;
	}

	public void setPhone(int phone) {
		Phone = phone;
	}

}