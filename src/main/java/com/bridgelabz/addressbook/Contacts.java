package com.bridgelabz.addressbook;

import java.time.LocalDate;

public class Contacts {

	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private LocalDate dateAdded;
	private Address address;

	public Contacts() {

	}

	public Contacts(String firstName, String lastName, String phoneNumber, String email, LocalDate dateAdded) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.dateAdded = dateAdded;
	}

	public Contacts(String firstName, String lastName, String phoneNumber, String email, Address address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
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

	public LocalDate getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Contacts that = (Contacts) obj;
		return phoneNumber.compareTo(that.phoneNumber) == 0 && email.compareTo(that.email) == 0
				&& firstName.compareTo(that.firstName) == 0 && lastName.compareTo(that.lastName) == 0;
	}

}
