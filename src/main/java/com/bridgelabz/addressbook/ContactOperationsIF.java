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

	public List<Contacts> readAddressBookDBData(I0Service ioservice) throws AddressBookException;

	public void updateAddressBook(String name, String phoneNumber) throws AddressBookException;

	public boolean checkAddressBookInSyncWithDB(String name) throws AddressBookException;

	public List<Contacts> getContactFromDateRange(String date) throws AddressBookException;

	public List<Contacts> getContactFromAddress(String city, String state) throws AddressBookException;

	public void addContactToDataBase(Contacts contacts, Address address, String addressBookName) throws AddressBookException;

}
