package com.bridgelabz.addressbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {

	public AddressBookDBService() {

	}

	private PreparedStatement contactData;

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

	private List<Contacts> getContactData(ResultSet resultSet) throws AddressBookException {
		List<Contacts> addressBookList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String phoneNumber = resultSet.getString("phone_number");
				String email = resultSet.getString("email");
				LocalDate dateAdded = resultSet.getDate("start").toLocalDate();
				Contacts contact = new Contacts();
				contact.setFirstName(firstName);
				contact.setLastName(lastName);
				contact.setPhoneNumber(phoneNumber);
				contact.setEmail(email);
				contact.setDateAdded(dateAdded);
				addressBookList.add(contact);

			}
		} catch (SQLSyntaxErrorException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.UNKOWN_DATABASE, "Error in databse");
		} catch (SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.SQL_EXCEPTION,
					"Syntax error in sql statement");
		}
		return addressBookList;
	}

	private List<Contacts> excecuteSqlQuery(String sql) throws AddressBookException {
		List<Contacts> contactList = null;
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			contactList = getContactData(resultSet);
		} catch (SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.SQL_EXCEPTION,
					"Syntax error in sql statement");
		}
		return contactList;
	}

	public List<Contacts> readAddressBook() throws AddressBookException {

		String sql = "SELECT * FROM contact";
		List<Contacts> addressBookList = new ArrayList<>();
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			addressBookList = getContactData(resultSet);

		} catch (SQLSyntaxErrorException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.UNKOWN_DATABASE, "Error in databse");
		} catch (SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.SQL_EXCEPTION,
					"Syntax error in sql statement");
		}
		return addressBookList;
	}

	public int updateContct(String name, String phoneNumber) throws AddressBookException {
		int result = 0;
		String sql = String.format("UPDATE contact SET phone_number = '%s' WHERE first_name ='%s';", phoneNumber, name);
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			result = statement.executeUpdate(sql);
		} catch (SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.SQL_EXCEPTION,
					"Syntax error in sql statement");
		}
		return result;
	}

	public List<Contacts> getContactData(String name) throws AddressBookException {
		List<Contacts> addressBookList = null;
		if (this.contactData == null)
			this.prepareStatementForContactData();
		try {
			contactData.setString(1, name);
			ResultSet resultSet = contactData.executeQuery();
			addressBookList = getContactData(resultSet);
		} catch (SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.SQL_EXCEPTION,
					"Syntax error in sql statement");
		}
		return addressBookList;

	}

	private void prepareStatementForContactData() throws AddressBookException {
		try {
			Connection connection = this.getConnection();
			String sql = "SELECT * FROM contact where first_name = ?;";
			contactData = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.SQL_EXCEPTION,
					"Syntax error in sql statement");
		}
	}

	public List<Contacts> getContactFromDateRange(String date) throws AddressBookException {
		String sql = String.format("SELECT * FROM contact WHERE start BETWEEN CAST('%s' AS DATE) AND DATE(NOW());",
				date);
		List<Contacts> contactList = excecuteSqlQuery(sql);
		return contactList;
	}

	public List<Contacts> getContactFromAddress(String city, String state) throws AddressBookException {
		String sql = String.format("SELECT * FROM contact NATURAL JOIN address WHERE city = '%s' OR state = '%s';",
				city, state);
		List<Contacts> contactList = excecuteSqlQuery(sql);
		return contactList;
	}

	public Contacts addContact(Contacts contacts, Address address, String addressBookName) throws AddressBookException {
		int contactId = -1;
		Connection connection = null;

		try {
			connection = this.getConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e1) {
			throw new AddressBookException(AddressBookException.ExceptionType.SQL_EXCEPTION,
					"Syntax error in sql statement");
		}

		try (Statement statement = connection.createStatement()) {
			int addressBookId = 0;
			String getId = String.format(
					"SELECT address_book_id FROM address_book_name WHERE address_book_name = '%s';", addressBookName);
			ResultSet result = statement.executeQuery(getId);
			while (result.next()) {
				addressBookId = result.getInt("address_book_id");
			}
			result.close();
			String sql = String.format(
					"INSERT INTO contact (first_name, last_name, phone_number, email, start, address_book_id) VALUES ('%s', '%s', '%s', '%s', CAST('%s' AS DATE), '%d');",
					contacts.getFirstName(), contacts.getLastName(), contacts.getPhoneNumber(), contacts.getEmail(),
					contacts.getDateAdded(), addressBookId);
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if (rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next())
					contactId = resultSet.getInt(1);
			}
		} catch (SQLException e) {

			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new AddressBookException(AddressBookException.ExceptionType.SQL_EXCEPTION,
						"Syntax error in sql statement");
			}
		}

		try (Statement statement = connection.createStatement()) {
			String sql = String.format("INSERT INTO address VALUES ('%d', '%s', '%s', '%s', '%s');", contactId,
					address.getPlace(), address.getCity(), address.getState(), address.getZipCode());
			statement.executeUpdate(sql);

			connection.commit();
			connection.close();

		} catch (SQLException exception) {
			exception.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e) {
				throw new AddressBookException(AddressBookException.ExceptionType.SQL_EXCEPTION,
						"Syntax error in sql statement");
			}
		}
		return contacts;
	}

}
