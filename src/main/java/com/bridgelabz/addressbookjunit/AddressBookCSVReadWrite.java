package com.bridgelabz.addressbookjunit;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.bridgelabz.addressbook.Contact;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
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
}
