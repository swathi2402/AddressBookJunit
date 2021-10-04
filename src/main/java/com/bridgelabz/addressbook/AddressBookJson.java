package com.bridgelabz.addressbook;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class AddressBookJson {

	public void writeToJson(String name) {

		final String CSV_PATH = "./" + name + ".csv";
		final String JSON_PATH = "./" + name + ".json";

		try (Reader reader = Files.newBufferedReader(Paths.get(CSV_PATH));) {

			CsvToBeanBuilder<Contact> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withType(Contact.class);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<Contact> csvToBean = csvToBeanBuilder.build();
			List<Contact> csvUsers = csvToBean.parse();

			Gson gson = new Gson();
			String json = gson.toJson(csvUsers);
			FileWriter writer = new FileWriter(JSON_PATH);
			writer.write(json);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readFromJson(String name) {
		final String JSON_PATH = "./" + name + ".json";
		Gson gson = new Gson();

		try {
			BufferedReader br = new BufferedReader(new FileReader(JSON_PATH));
			Contact[] usrObj = gson.fromJson(br, Contact[].class);
			List<Contact> csvUserList = Arrays.asList(usrObj);
			System.out.println(csvUserList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
