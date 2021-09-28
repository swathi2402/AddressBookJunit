package com.bridgelabz.addressbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {

	public AddressBookDBService() {

	}

	private Connection getConnection() {
		String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?useSSL=false";
		String userName = "root";
		String password = "swathi*123";
		Connection connection = null;

		System.out.println("Connecting to database" + jdbcURL);
		try {
			connection = DriverManager.getConnection(jdbcURL, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connection is successfull" + connection);

		return connection;
	}

	public List<Contact> readAddressBook() {

		String sql = "SELECT * FROM contact";
		List<Contact> addressBookList = new ArrayList<>();
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String phoneNumber = resultSet.getString("phone_number");
				String email = resultSet.getString("email");
				Contact contact = new Contact();
				contact.setFirstName(firstName);
				contact.setLastName(lastName);
				contact.setPhoneNumber(phoneNumber);
				contact.setEmail(email);
				addressBookList.add(contact);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}

	public int updateContct(String name, String phoneNumber) {
		int result = 0;
		String sql = String.format("UPDATE contact SET phone_number = '%s' WHERE first_name ='%s';", phoneNumber, name);
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			result = statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Contact> getContactData(String name) {
		List<Contact> addressBookList = null;
		String sql = "SELECT * FROM contact";
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			addressBookList = getContactData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();		}
		return addressBookList;
	}

	private List<Contact> getContactData(ResultSet resultSet) {
		List<Contact> addressBookList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String phoneNumber = resultSet.getString("phone_number");
				String email = resultSet.getString("email");
				Contact contact = new Contact();
				contact.setFirstName(firstName);
				contact.setLastName(lastName);
				contact.setPhoneNumber(phoneNumber);
				contact.setEmail(email);
				addressBookList.add(contact);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressBookList;
	}

}
