package com.bridgelabz.addressbook;

public interface ContactOperationsIF {

	public void addAddressBook(String addressBookName);

	public void addContact(String addressBookName);

	public void checkToEdit(String addressBookName);

	public void deleteContact(String addressBookName);

	public void searchPerson(String nameToSearch);

	public void getPersonsInCity(String city);

	public void getPersonsInState(String state);

	public void getCountInCity(String cityName);

	public void getCountInState(String stateName);

	public void sortByName(String bookName);

	public void sortByCity(String nameOfCity);

	public void sortByState(String nameOfState);

}
