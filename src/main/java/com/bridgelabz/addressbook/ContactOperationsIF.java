package com.bridgelabz.addressbook;

import com.bridgelabz.addressbook.ContactOperationsImpl.I0Service;

public interface ContactOperationsIF {

	public void addAddressBook(String addressBookName);

	public void writeData(I0Service ioservice, String addressBookName);

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
	
	public long readData(I0Service ioservice, String name);
	
	public long countEntries(I0Service ioservice, String name);


}
