package com.bridgelabz.addressbook;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class AddressBookTest {

	@Test
	public void givenContactWriteToTheFile() {
		Contact[] contacts = {
				new Contact("Swathi", "Hebbar", "Navunda", "Kundapura", "Karnataka", "567567", "1234567890",
						"swathi@gmail.com"),
				new Contact("Abc", "Def", "Dfgh", "Kundapura", "Karnataka", "123123", "4567890123", "abc@gmail.com"),
				new Contact("Xyz", "Abc", "Resdf", "Gfhuj", "Fdredf", "678678", "7890123456", "xyz@gmail.com") };
		AddressBookService addressBookService;
		addressBookService = new AddressBookService(Arrays.asList(contacts));
		addressBookService.writeData(AddressBookService.I0Service.FILE_I0, "Addressbook");
		long entries = addressBookService.countEntries(AddressBookService.I0Service.FILE_I0, "Addressbook");
		assertEquals(3, entries);
	}

	@Test
	public void givenFileOnReadingFromFileShouldMatchEContactCount() {
		AddressBookService addressBookService = new AddressBookService();
		long entries = addressBookService.readData(AddressBookService.I0Service.FILE_I0, "Addressbook");
		assertEquals(3, entries);
	}
}
