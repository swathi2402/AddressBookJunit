package com.bridgelabz.addressbook;

import com.opencsv.bean.CsvBindByName;

public class Contact {

	@Override
	public String toString() {
		return "firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", city=" + city
				+ ", state=" + state + ", ZIP=" + ZIP + ", phoneNumber=" + phoneNumber + ", email=" + email;
	}

	@CsvBindByName
	private String firstName;
	@CsvBindByName
	private String lastName;
	@CsvBindByName
	private String address;
	@CsvBindByName
	private String city;
	@CsvBindByName
	private String state;
	@CsvBindByName
	private String ZIP;
	@CsvBindByName
	private String phoneNumber;
	@CsvBindByName
	private String email;
	

	public Contact(String firstName, String lastName, String address, String city, String state, String zIP,
			String phoneNumber, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		ZIP = zIP;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public Contact() {
		
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZIP() {
		return ZIP;
	}

	public void setZIP(String zIP) {
		ZIP = zIP;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
