package com.bridgelabz.addressbook;

import java.sql.SQLException;
import java.util.List;

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

	public List<Contact> readAddressBookDBData(I0Service ioservice) throws SQLException;

	public void updateAddressBook(String name, String phoneNumber);

	public boolean checkAddressBookInSyncWithDB(String name);

	public List<Contact> getContactFromDateRange(String date);

	public List<Contact> getContactFromAddress(String city, String state);

	public void addContactToDataBase(String firstName, String lastName, String phoneNumber, String email, int addressBookId,
			String type, String place, String city, String state, String zipCode);

}
