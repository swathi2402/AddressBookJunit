package com.bridgelabz.addressbookjunit;

import java.util.List;

import com.bridgelabz.addressbook.Contact;

public class AddressBookService {

	public enum I0Service {
		CONSOLE_IO, FILE_I0, DB_I0, REST_I0
	}

	private List<Contact> addressBookList;
	
	public AddressBookService() {
		
	}

	public AddressBookService(List<Contact> addressBookList) {
		this.addressBookList = addressBookList;
	}

	public void writeData(I0Service ioservice, String name) {
		if (ioservice.equals(I0Service.FILE_I0)) {
			new AdressBookFileIOService().writeData(addressBookList, name);
		}
	}

	public long readData(I0Service ioservice, String name) {
		if (ioservice.equals(I0Service.FILE_I0)) {
			this.addressBookList = new AdressBookFileIOService().readData(name);
			System.out.println("Contact Details: ");
			this.addressBookList.forEach(contact -> System.out.println(contact));
		}
		return this.addressBookList.size();
	}

	public long countEntries(I0Service ioservice, String name) {
		if (ioservice.equals(I0Service.FILE_I0)) {
			return new AdressBookFileIOService().countEntries(name);
		}
		return 0;
	}
}
