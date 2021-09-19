package com.bridgelabz.addressbook;

import java.util.Arrays;

import org.junit.Test;

public class AddressBookTest {
	@Test
	public void givenContactWriteToTheFile(){
		Contact[] contacts = {
				new Contact("Swathi", "Hebbar", "Navunda", "Kundapura", "Karnataka", "567567", "1234567890", "swathi@gmail.com"),
				new Contact("Abc", "Def", "Dfgh", "Kundapura", "Karnataka", "123123", "4567890123", "abc@gmail.com"),
				new Contact("Xyz", "Abc", "Resdf", "Gfhuj", "Fdredf", "678678", "7890123456", "xyz@gmail.com")};
		AddressBookService addressBookService;
		addressBookService = new AddressBookService(Arrays.asList(contacts));
		addressBookService.writeData(AddressBookService.I0Service.FILE_I0, "Addressbook");
	}
}
