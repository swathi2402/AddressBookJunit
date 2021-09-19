package com.bridgelabz.addressbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AdressBookFileIOService {

	public void writeData(List<Contact> addressBook, String name) {
		StringBuffer contactBuffer = new StringBuffer();
		addressBook.forEach(contact -> {
			String contactDataString = contact.toString().concat("\n");
			contactBuffer.append(contactDataString);
		});

		try {
			Files.write(Paths.get(name), contactBuffer.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
