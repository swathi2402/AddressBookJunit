package com.bridgelabz.addressbook;

public class Address {

	private String place;

	private String city;
	private String state;
	private String zipCode;

	public Address(String place, String city, String state, String zipCode) {
		super();
		this.place = place;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
