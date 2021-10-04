package com.bridgelabz.addressbookjunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.bridgelabz.addressbook.Address;
import com.bridgelabz.addressbook.AddressBookCSVReadWrite;
import com.bridgelabz.addressbook.AddressBookException;
import com.bridgelabz.addressbook.AddressBookJson;
import com.bridgelabz.addressbook.Contact;
import com.bridgelabz.addressbook.ContactOperationsIF;
import com.bridgelabz.addressbook.ContactOperationsImpl;
import com.bridgelabz.addressbook.ContactOperationsImpl.I0Service;
import com.bridgelabz.addressbook.Contacts;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class AddressBookTest {
	ContactOperationsIF contactOperations;
	@Before
	public void setUp() throws Exception {
		contactOperations = new ContactOperationsImpl();
	}

	@Test
	public void givenContactWriteToTheFile() {
		Contact[] contacts = {
				new Contact("Swathi", "Hebbar", "Navunda", "Kundapura", "Karnataka", "567567", "1234567890",
						"swathi@gmail.com"),
				new Contact("Abc", "Def", "Dfgh", "Kundapura", "Karnataka", "123123", "4567890123", "abc@gmail.com"),
				new Contact("Xyz", "Abc", "Resdf", "Gfhuj", "Fdredf", "678678", "7890123456", "xyz@gmail.com") };
		ContactOperationsIF contactOperations;
		contactOperations = new ContactOperationsImpl(Arrays.asList(contacts));
		contactOperations.writeData(ContactOperationsImpl.I0Service.FILE_I0, "Addressbook");
		long entries = contactOperations.countEntries(ContactOperationsImpl.I0Service.FILE_I0, "Addressbook");
		assertEquals(3, entries);
	}

	@Test
	public void givenFileOnReadingFromFileShouldMatchEContactCount() {
		long entries = contactOperations.readData(ContactOperationsImpl.I0Service.FILE_I0, "Addressbook");
		assertEquals(3, entries);
	}

	@Test
	public void addContactThroughConsole() {
		contactOperations.addAddressBook("Addressbook");
		contactOperations.writeData(ContactOperationsImpl.I0Service.CONSOLE_IO, "Addressbook");
	}

	@Test
	public void givenContactDetails_AbilityToCreateCSVFile()
			throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
		contactOperations.writeData(ContactOperationsImpl.I0Service.CSV_IO, "Addressbook");
	}

	@Test
	public void givenCSVFile_AbilityToReadCSVFile() throws IOException, CsvException {
		AddressBookCSVReadWrite addressBookCSVReadWrite = new AddressBookCSVReadWrite();
		addressBookCSVReadWrite.readFromCSV("addressbook");
	}

	@Test
	public void givenContactDetailsOfCSVFile_AbilityToCreateJsonFile() {
		ContactOperationsIF contactOperations = new ContactOperationsImpl();
		contactOperations.writeData(ContactOperationsImpl.I0Service.JSON_IO, "Addressbook");
	}
	
	@Test
	public void givenContactDetailsOfCSVFile_AbilityToReadJsonFile() {
		AddressBookJson addressBookJson = new AddressBookJson();
		addressBookJson.readFromJson("addressbook");
	}
	
	@Test
	public void givenAddressBookDB_WhenRetrieved_ShouldMatchContactCount() throws AddressBookException {
		List<Contacts> addressBookData = contactOperations.readAddressBookDBData(I0Service.DB_IO);
		assertEquals(4, addressBookData.size());
	}
	
	@Test
	public void whenContactUpdated_ShouldSyncWithDB() throws AddressBookException {
		contactOperations.readAddressBookDBData(I0Service.DB_IO);
		contactOperations.updateAddressBook("Swathi", "9922334455");
		boolean result = contactOperations.checkAddressBookInSyncWithDB("Swathi");
		assertTrue(result);
	}
	
	@Test
	public void getContactCountFromAddressBook_GivenADateRange() throws AddressBookException {
		contactOperations.readAddressBookDBData(I0Service.DB_IO);
		String date = "2020-01-01";
		List<Contacts> contactList = contactOperations.getContactFromDateRange(date);
		assertEquals(3, contactList.size());
	}
	
	@Test
	public void getContactFromAddressBook_GivenCityOrState() throws AddressBookException {
		contactOperations.readAddressBookDBData(I0Service.DB_IO);
		List<Contacts> contactList = contactOperations.getContactFromAddress("Kundapura", "Karnataka");
		assertEquals(2, contactList.size());
	}
	
	@Test
	public void givenNewContact_WhenAdded_ShouldBeInSyncWithDB() throws AddressBookException {
		contactOperations.readAddressBookDBData(I0Service.DB_IO);
		LocalDate date = LocalDate.of(2020, 1, 8);
		Contacts contacts = new Contacts("Spandana", "Shasthri", "9124565432", "spandana@gmail.com", date);
		Address address = new Address("Abcd", "Maduri", "TamilNadu", "676567");
		contactOperations.addContactToDataBase(contacts, address, "addresssBook2");
		boolean result = contactOperations.checkAddressBookInSyncWithDB("Spandana");
		assertTrue(result);
	}
}
