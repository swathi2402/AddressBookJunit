package com.bridgelabz.addressbook;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ContactOperationsImpl implements ContactOperationsIF {

	public enum I0Service {
		CONSOLE_IO, FILE_I0, CSV_IO, JSON_IO, DB_IO
	}

	private List<Contact> addressBookList;
	private List<Contacts> addressBookList1;
	private AddressBookDBService addressBookDBService = new AddressBookDBService();

	public ContactOperationsImpl() {

	}

	public ContactOperationsImpl(List<Contact> addressBookList) {
		this.addressBookList = addressBookList;

	}

	Scanner scanner = new Scanner(System.in);
	private Map<String, List<Contact>> addressBook = new HashMap<String, List<Contact>>();
	private Map<String, List<Contact>> personsInCity = new HashMap<String, List<Contact>>();
	private Map<String, List<Contact>> personsInState = new HashMap<String, List<Contact>>();

	public void addAddressBook(String addressBookName) {
		if (addressBook.keySet().stream().anyMatch(n -> (n.equals(addressBookName)))) {
			System.out.println(addressBookName + " Address Book alredy exists");
			return;
		} else {
			System.out.println(addressBookName + " Address Book created");
			addressBook.put(addressBookName, new ArrayList<Contact>());
		}
	}

	@Override
	public void writeData(I0Service ioservice, String addressBookName) {
		switch (ioservice) {
		case FILE_I0:
			new AdressBookFileIOService().writeData(addressBookList, addressBookName);
			break;

		case CONSOLE_IO:
			addContact(addressBookName);
			break;

		case CSV_IO:
			try {
				new AddressBookCSVReadWrite().writeToCSV(addressBookName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case JSON_IO:
			new AddressBookJson().writeToJson(addressBookName);
			break;

		default:
			break;
		}

	}

	public List<Contacts> readAddressBookDBData(I0Service ioservice) throws SQLException {
		if (ioservice.equals(I0Service.DB_IO))
			addressBookList1 = addressBookDBService.readAddressBook();
		return addressBookList1;
	}

	public Contact createContact() {
		System.out.println(
				"Enter details in the order First Name, Lsat Name, Address, City, State, Pincode, Phone Number, Email Address");

		String firstName = scanner.nextLine();
		String lastName = scanner.nextLine();
		String address = scanner.nextLine();
		String city = scanner.nextLine();
		String state = scanner.nextLine();
		String ZIP = scanner.nextLine();
		String phoneNumber = scanner.nextLine();
		String email = scanner.nextLine();

		Contact newContact = new Contact(firstName, lastName, address, city, state, ZIP, phoneNumber, email);
		return newContact;
	}

	private void addContact(String addressBookName) {
		Contact newContact = createContact();

		boolean isPresent = false;
		for (int index = 0; index < addressBook.get(addressBookName).size(); index++) {
			if (newContact.getFirstName().equals(addressBook.get(addressBookName).get(index).getFirstName())) {
				System.out.println("Contact for " + newContact.getFirstName() + " " + " is already exists");
				isPresent = true;
				break;
			}
		}

		if (!isPresent) {
			addressBook.get(addressBookName).add(newContact);
			System.out
					.println("Contact for " + newContact.getFirstName() + " " + newContact.getLastName() + " is added");

			if (personsInCity.get(newContact.getCity()) == null) {
				personsInCity.put(newContact.getCity(), new ArrayList<Contact>());
			}
			personsInCity.get(newContact.getCity()).add(newContact);

			if (personsInState.get(newContact.getState()) == null) {
				personsInState.put(newContact.getState(), new ArrayList<Contact>());
			}
			personsInState.get(newContact.getState()).add(newContact);

		}
	}

	@Override
	public void checkToEdit(String addressBookName) {

		if (addressBook.get(addressBookName).isEmpty()) {
			System.out.print("Address book is empty to edit");
		} else {
			boolean isValid = false;
			System.out.println("Enter the First Name of the contact to be edit:");
			String firstName = scanner.next();
			int size = addressBook.get(addressBookName).size();
			for (int index = 0; index < size; index++) {
				String name = addressBook.get(addressBookName).get(index).getFirstName();
				if (firstName.equals(name)) {
					editContact(addressBookName, name, index);
					isValid = true;
					break;
				}
			}
			if (!isValid) {
				System.out.print("Enter valid name");
			}
		}
	}

	private void editContact(String addressBookName, String name, int index) {

		System.out.println(
				"Enter details in the order Lsat Name, Address, City, State, Pincode, Phone Number, Email Address");

		String lastName = scanner.nextLine();
		String address = scanner.nextLine();
		String city = scanner.nextLine();
		String state = scanner.nextLine();
		String ZIP = scanner.nextLine();
		String phoneNumber = scanner.nextLine();
		String email = scanner.nextLine();

		Contact contact = addressBook.get(addressBookName).get(index);
		addressBook.get(addressBookName).remove(index);
		Contact contactToBeEdit = new Contact(name, lastName, address, city, state, ZIP, phoneNumber, email);
		addressBook.get(addressBookName).add(contactToBeEdit);
		System.out.println("Contact of " + name + " has been edited");

		personsInCity.get(contact.getCity()).remove(contact);
		personsInState.get(contact.getState()).remove(contact);
		if (personsInCity.get(city) == null) {
			personsInCity.put(city, new ArrayList<Contact>());
		}
		personsInCity.get(city).add(contactToBeEdit);

		if (personsInState.get(state) == null) {
			personsInState.put(state, new ArrayList<Contact>());
		}
		personsInState.get(state).add(contactToBeEdit);

	}

	@Override
	public void updateAddressBook(String name, String phoneNumber) {
		int result = addressBookDBService.updateContct(name, phoneNumber);
		if (result == 0)
			return;
		Contacts contact = this.getContact(name);
		if (contact != null)
			contact.setPhoneNumber(phoneNumber);
	}

	private Contacts getContact(String name) {
		return addressBookList1.stream().filter(contactItem -> contactItem.getFirstName().equals(name)).findFirst()
				.orElse(null);
	}

	@Override
	public boolean checkAddressBookInSyncWithDB(String name) {
		List<Contacts> addressBookDataList = addressBookDBService.getContactData(name);
		return addressBookDataList.get(0).equals(getContact(name));
	}

	@Override
	public void deleteContact(String addressBookName) {

		if (addressBook.get(addressBookName).isEmpty()) {
			System.out.print("Address book is empty to delete");
		} else {
			boolean isValid = false;
			System.out.println("Enter the First Name of the contact to be delete:");
			String firstName = scanner.nextLine();
			int size = addressBook.get(addressBookName).size();
			for (int index = 0; index < size; index++) {
				String name = addressBook.get(addressBookName).get(index).getFirstName();
				if (firstName.equals(name)) {
					Contact deletedContact = addressBook.get(addressBookName).remove(index);
					System.out.println("Contact of " + deletedContact.getFirstName() + " has been deleted");

					personsInCity.get(deletedContact.getCity()).remove(deletedContact);
					personsInState.get(deletedContact.getState()).remove(deletedContact);
					isValid = true;
					break;
				}
			}
			if (!isValid) {
				System.out.println("Enter valid name");
			}
		}
	}

	@Override
	public void searchPerson(String nameToSearch) {

		for (Entry<String, List<Contact>> entry : addressBook.entrySet()) {

			for (int index = 0; index < entry.getValue().size(); index++) {
				if (nameToSearch.equals(entry.getValue().get(index).getFirstName())) {
					System.out.println("Address Book name: " + entry.getKey());
					System.out.println("Person Name: " + entry.getValue().get(index).getFirstName());
					System.out.println("State: " + entry.getValue().get(index).getState());
					System.out.println("City: " + entry.getValue().get(index).getCity());
				} else
					System.out.println("No Such person exits in addressbook " + entry.getKey());

			}
		}
	}

	@Override
	public void getPersonsInCity(String city) {
		if (personsInCity.get(city) != null) {
			System.out.println("Persons in city " + city + " :");
			personsInCity.get(city).forEach(n -> System.out.println(n.getFirstName() + " " + n.getLastName()));
		} else {
			System.out.println("There is no person in city " + city);
		}
	}

	@Override
	public void getPersonsInState(String state) {
		if (personsInState.get(state) != null) {
			System.out.println("Persons in state " + state + " :");
			personsInState.get(state).forEach(n -> System.out.println(n.getFirstName() + " " + n.getLastName()));
		} else {
			System.out.println("There is no person in state " + state);
		}
	}

	@Override
	public void getCountInCity(String cityName) {
		if (personsInCity.get(cityName) != null) {
			System.out.println("Total count persons in " + cityName + " is: " + personsInCity.get(cityName).size());
		} else {
			System.out.println("No such city exists");
		}
	}

	@Override
	public void getCountInState(String stateName) {
		if (personsInState.get(stateName) != null) {
			System.out.println("Total count persons in " + stateName + " is: " + personsInState.get(stateName).size());
		} else {
			System.out.println("No such state exists");
		}
	}

	@Override
	public void sortByName(String bookName) {
		List<Contact> contactArray = addressBook.get(bookName);
		List<Contact> sortedNameList = contactArray.stream()
				.sorted((s1, s2) -> s1.getFirstName().compareTo(s2.getFirstName())).collect(Collectors.toList());
		System.out.println("Persons in the AddressBook " + bookName + " in sorted order: ");
		sortedNameList.stream().forEach(n -> System.out.println(n.getFirstName() + " " + n.getLastName()));
	}

	@Override
	public void sortByCity(String nameOfCity) {
		List<Contact> contactArray = personsInCity.get(nameOfCity);
		List<Contact> sortedCityList = contactArray.stream().sorted((s1, s2) -> (s1.getCity().compareTo(s2.getCity())))
				.collect(Collectors.toList());
		System.out.println("Persons in the City " + nameOfCity + " in sorted order: ");
		sortedCityList.stream().forEach(n -> System.out.println(n.getFirstName() + " " + n.getLastName()));
	}

	@Override
	public void sortByState(String nameOfState) {
		List<Contact> contactArray = personsInState.get(nameOfState);
		List<Contact> sortedStateList = contactArray.stream()
				.sorted((s1, s2) -> (s1.getState().compareTo(s2.getState()))).collect(Collectors.toList());
		System.out.println("Persons in the State " + nameOfState + " in sorted order: ");
		sortedStateList.stream().forEach(n -> System.out.println(n.getFirstName() + " " + n.getLastName()));
	}

	public long countEntries(I0Service ioservice, String name) {
		if (ioservice.equals(I0Service.FILE_I0)) {
			return new AdressBookFileIOService().countEntries(name);
		}
		return 0;
	}

	public long readData(I0Service ioservice, String name) {
		if (ioservice.equals(I0Service.FILE_I0)) {
			this.addressBookList = new AdressBookFileIOService().readData(name);
			System.out.println("Contact Details: ");
			this.addressBookList.forEach(contact -> System.out.println(contact));
		}
		return this.addressBookList.size();
	}

	@Override
	public List<Contacts> getContactFromDateRange(String date) {
		List<Contacts> contactList = addressBookDBService.getContactFromDateRange(date);
		return contactList;
	}

	@Override
	public List<Contacts> getContactFromAddress(String city, String state) {
		List<Contacts> contactList = addressBookDBService.getContactFromAddress(city, state);
		return contactList;
	}

	@Override
	public void addContactToDataBase(Contacts contacts, Address address, String addressBookName) throws SQLException {
		addressBookList1.add(addressBookDBService.addContact(contacts, address, addressBookName));

	}

}
