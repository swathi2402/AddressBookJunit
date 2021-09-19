package com.bridgelabz.addressbook;

import java.io.File;
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
	
	public long countEntries(String name) {
		long enteries = 0;
		try {
			enteries = Files.lines(new File(name).toPath()).count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return enteries;
	}
}
