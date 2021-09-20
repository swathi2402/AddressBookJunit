package com.bridgelabz.addressbookjunit;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.bridgelabz.addressbook.Contact;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
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
			StatefulBeanToCsv<Contact> beanToCsv = new StatefulBeanToCsvBuilder(writer)
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

			List<Contact> contacts = new ArrayList<>();

			contacts.add(new Contact("Swathi", "Hebbar", "Navunda", "Kundapura", "Karnataka", "567567", "1234567890",
					"swathi@gmail.com"));
			contacts.add(new Contact("Abc", "Def", "Dfgh", "Kundapura", "Karnataka", "123123", "4567890123",
					"abc@gmail.com"));
			contacts.add(
					new Contact("Xyz", "Abc", "Resdf", "Gfhuj", "Fdredf", "678678", "7890123456", "xyz@gmail.com"));

			beanToCsv.write(contacts);
		}
	}
	
	public void readFromCSV(String name) throws IOException, CsvException {
		final String PATH = "./" + name + ".csv";
		try (Reader reader = Files.newBufferedReader(Paths.get(PATH));
				CSVReader csvReader = new CSVReader(reader);) {

			List<String[]> contacts = csvReader.readAll();
			for (String[] contact : contacts) {
				System.out.println("firstName: " + contact[0]);
				System.out.println("lastName: " + contact[1]);
				System.out.println("address: " + contact[2]);
				System.out.println("city: " + contact[3]);
				System.out.println("state: " + contact[4]);
				System.out.println("ZIP: " + contact[5]);
				System.out.println("phoneNumber: " + contact[6]);
				System.out.println("email: " + contact[7]);			
				System.out.println("-------------------------------");
			}

		}
	}
}
