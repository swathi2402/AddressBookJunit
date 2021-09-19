package com.bridgelabz.addressbookjunit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.bridgelabz.addressbook.Contact;

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

	public List<Contact> readData(String name) {
		List<Contact> contactDetails = new ArrayList<Contact>();
		try {
			Files.lines(new File(name).toPath()).map(line -> line.trim()).forEach(line -> {
				Contact tempContact = new Contact(line.split(",")[0].split("=")[1], line.split(",")[1].split("=")[1],
						line.split(",")[2].split("=")[1], line.split(",")[3].split("=")[1],
						line.split(",")[4].split("=")[1], line.split(",")[5].split("=")[1],
						line.split(",")[6].split("=")[1], line.split(",")[7].split("=")[1]);
				contactDetails.add(tempContact);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contactDetails;
	}
}
