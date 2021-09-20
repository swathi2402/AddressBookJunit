package com.bridgelabz.addressbookjunit;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import com.bridgelabz.addressbook.Contact;
import com.bridgelabz.addressbook.ContactOperationsIF;
import com.bridgelabz.addressbook.ContactOperationsImpl;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class AddressBookTest {

	@Test
	public void givenContactWriteToTheFile() {
		Contact[] contacts = {
				new Contact("Swathi", "Hebbar", "Navunda", "Kundapura", "Karnataka", "567567", "1234567890",
						"swathi@gmail.com"),
				new Contact("Abc", "Def", "Dfgh", "Kundapura", "Karnataka", "123123", "4567890123", "abc@gmail.com"),
				new Contact("Xyz", "Abc", "Resdf", "Gfhuj", "Fdredf", "678678", "7890123456", "xyz@gmail.com") };
		ContactOperationsIF contactOperations;
		contactOperations = new ContactOperationsImpl(Arrays.asList(contacts));
		contactOperations.addContact(ContactOperationsImpl.I0Service.FILE_I0, "Addressbook");
		long entries = contactOperations.countEntries(ContactOperationsImpl.I0Service.FILE_I0, "Addressbook");
		assertEquals(3, entries);
	}

	@Test
	public void givenFileOnReadingFromFileShouldMatchEContactCount() {
		ContactOperationsIF contactOperations = new ContactOperationsImpl();
		long entries = contactOperations.readData(ContactOperationsImpl.I0Service.FILE_I0, "Addressbook");
		assertEquals(3, entries);
	}
	
	@Test
	public void addContactThroughConsole() {
		ContactOperationsIF contactOperations = new ContactOperationsImpl();
		contactOperations.addAddressBook("Addressbook");
		contactOperations.addContact(ContactOperationsImpl.I0Service.CONSOLE_IO, "Addressbook");
	}
	
	@Test
	public void givenContactDetails_AbilityToCreateCSVFile() throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
		AddressBookCSVReadWrite addressBookCSVReadWrite = new AddressBookCSVReadWrite();
		addressBookCSVReadWrite.writeToCSV("addressbook");
	}
	
	@Test
	public void givenCSVFile_AbilityToReadCSVFile() throws IOException, CsvException {
		AddressBookCSVReadWrite addressBookCSVReadWrite = new AddressBookCSVReadWrite();
		addressBookCSVReadWrite.readFromCSV("addressbook");
	}
}
