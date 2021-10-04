package com.bridgelabz.addressbook;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class AddressBookCSVReadWrite {

	public void writeToCSV(String name)
			throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		final String PATH = "./" + name + ".csv";
		try (Writer writer = Files.newBufferedWriter(Paths.get(PATH));) {
			StatefulBeanToCsv<Contacts> beanToCsv = new StatefulBeanToCsvBuilder(writer)
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

			List<Contacts> contacts = new ArrayList<>();

			Address address1 = new Address("Navunda", "Kundapura", "Karnataka", "567567");
			Address address2 = new Address("Dfgh", "Kundapura", "Karnataka", "123123");
			Address address3 = new Address("Resdf", "Gfhuj", "Fdredf", "678678");

			contacts.add(new Contacts("Swathi", "Hebbar", "1234567890", "swathi@gmail.com", address1));
			contacts.add(new Contacts("Abc", "Def", "4567890123", "abc@gmail.com", address2));
			contacts.add(new Contacts("Xyz", "Abc", "7890123456", "xyz@gmail.com", address3));

			beanToCsv.write(contacts);
		}

	}

	public void readFromCSV(String name) throws IOException, CsvException {
		final String PATH = "./" + name + ".csv";

		try (Reader reader = Files.newBufferedReader(Paths.get(PATH));) {
			CsvToBean<Contacts> csvToBean = new CsvToBeanBuilder(reader).withType(Contacts.class)
					.withIgnoreLeadingWhiteSpace(true).build();

			List<Contacts> contacts = csvToBean.parse();
			for (Contacts contact : contacts) {
				System.out.println("firstName: " + contact.getFirstName());
				System.out.println("lastName: " + contact.getLastName());
				System.out.println("address: " + contact.getAddress().getPlace());
				System.out.println("city: " + contact.getAddress().getCity());
				System.out.println("state: " + contact.getAddress().getState());
				System.out.println("ZIP: " + contact.getAddress().getZipCode());
				System.out.println("phoneNumber: " + contact.getPhoneNumber());
				System.out.println("email: " + contact.getEmail());
				System.out.println("-------------------------------");
			}

		}
	}
}
