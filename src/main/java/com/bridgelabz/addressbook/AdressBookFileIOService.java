package com.bridgelabz.addressbook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AdressBookFileIOService {

	public void writeData(List<Contacts> addressBookList, String name) {
		StringBuffer contactBuffer = new StringBuffer();
		addressBookList.forEach(contact -> {
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

	public List<Contacts> readData(String name) {
		List<Contacts> contactDetails = new ArrayList<Contacts>();
		try {
			Files.lines(new File(name).toPath()).map(line -> line.trim()).forEach(line -> {
				Contacts tempContact = new Contacts();
						tempContact.setFirstName(line.split(",")[0].split("=")[1]);
						tempContact.setLastName(line.split(",")[1].split("=")[1]);
						tempContact.setPhoneNumber(line.split(",")[2].split("=")[1]);
						tempContact.setEmail(line.split(",")[3].split("=")[1]);

				contactDetails.add(tempContact);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contactDetails;
	}
}
