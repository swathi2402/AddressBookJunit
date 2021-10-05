package com.bridgelabz.addressbook;

import com.opencsv.bean.CsvBindByName;

public class Address {

	@CsvBindByName
	private String place;
	@CsvBindByName
	private String city;
	@CsvBindByName
	private String state;
	@CsvBindByName
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
