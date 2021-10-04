package com.bridgelabz.addressbook;

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

	private List<Contacts> addressBookList;
	private AddressBookDBService addressBookDBService = new AddressBookDBService();

	public ContactOperationsImpl() {

	}

	public ContactOperationsImpl(List<Contacts> addressBookList) {
		this.addressBookList = addressBookList;

	}

	Scanner scanner = new Scanner(System.in);
	private Map<String, List<Contacts>> addressBook = new HashMap<String, List<Contacts>>();
	private Map<String, List<Contacts>> personsInCity = new HashMap<String, List<Contacts>>();
	private Map<String, List<Contacts>> personsInState = new HashMap<String, List<Contacts>>();

	public void addAddressBook(String addressBookName) {
		if (addressBook.keySet().stream().anyMatch(n -> (n.equals(addressBookName)))) {
			System.out.println(addressBookName + " Address Book alredy exists");
			return;
		} else {
			System.out.println(addressBookName + " Address Book created");
			addressBook.put(addressBookName, new ArrayList<Contacts>());
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

	public List<Contacts> readAddressBookDBData(I0Service ioservice) throws AddressBookException {
		if (ioservice.equals(I0Service.DB_IO))
			addressBookList = addressBookDBService.readAddressBook();
		return addressBookList;
	}

	public Contacts createContact() {
		System.out.println(
				"Enter details in the order First Name, Lsat Name, Address, City, State, Pincode, Phone Number, Email Address");

		String firstName = scanner.nextLine();
		String lastName = scanner.nextLine();
		String place = scanner.nextLine();
		String city = scanner.nextLine();
		String state = scanner.nextLine();
		String zipCode = scanner.nextLine();
		String phoneNumber = scanner.nextLine();
		String email = scanner.nextLine();

		Address address = new Address(place, city, state, zipCode);
		Contacts newContact = new Contacts(firstName, lastName, phoneNumber, email, address);
		return newContact;
	}

	private void addContact(String addressBookName) {
		Contacts newContact = createContact();

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

			if (personsInCity.get(newContact.getAddress().getCity()) == null) {
				personsInCity.put(newContact.getAddress().getCity(), new ArrayList<Contacts>());
			}
			personsInCity.get(newContact.getAddress().getCity()).add(newContact);

			if (personsInState.get(newContact.getAddress().getState()) == null) {
				personsInState.put(newContact.getAddress().getState(), new ArrayList<Contacts>());
			}
			personsInState.get(newContact.getAddress().getState()).add(newContact);

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
		String place = scanner.nextLine();
		String city = scanner.nextLine();
		String state = scanner.nextLine();
		String zipCode = scanner.nextLine();
		String phoneNumber = scanner.nextLine();
		String email = scanner.nextLine();

		Contacts contact = addressBook.get(addressBookName).get(index);
		addressBook.get(addressBookName).remove(index);
		Address address = new Address(place, city, state, zipCode);
		Contacts contactToBeEdit = new Contacts(name, lastName, phoneNumber, email, address);
		addressBook.get(addressBookName).add(contactToBeEdit);
		System.out.println("Contact of " + name + " has been edited");

		personsInCity.get(contact.getAddress().getCity()).remove(contact);
		personsInState.get(contact.getAddress().getState()).remove(contact);
		if (personsInCity.get(city) == null) {
			personsInCity.put(city, new ArrayList<Contacts>());
		}
		personsInCity.get(city).add(contactToBeEdit);

		if (personsInState.get(state) == null) {
			personsInState.put(state, new ArrayList<Contacts>());
		}
		personsInState.get(state).add(contactToBeEdit);

	}

	@Override
	public void updateAddressBook(String name, String phoneNumber) throws AddressBookException {
		if (name == "")
			throw new AddressBookException(AddressBookException.ExceptionType.EMPTY, "Name is empty");
		int result = addressBookDBService.updateContct(name, phoneNumber);
		if (result == 0)
			throw new AddressBookException(AddressBookException.ExceptionType.NOT_EXISTS, "Such name not exists or enterd null");
		Contacts contact = this.getContact(name);
		if (contact != null)
			contact.setPhoneNumber(phoneNumber);
	}

	private Contacts getContact(String name) {
		return addressBookList.stream().filter(contactItem -> contactItem.getFirstName().equals(name)).findFirst()
				.orElse(null);
	}

	@Override
	public boolean checkAddressBookInSyncWithDB(String name) throws AddressBookException {
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
					Contacts deletedContact = addressBook.get(addressBookName).remove(index);
					System.out.println("Contact of " + deletedContact.getFirstName() + " has been deleted");

					personsInCity.get(deletedContact.getAddress().getCity()).remove(deletedContact);
					personsInState.get(deletedContact.getAddress().getState()).remove(deletedContact);
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

		for (Entry<String, List<Contacts>> entry : addressBook.entrySet()) {

			for (int index = 0; index < entry.getValue().size(); index++) {
				if (nameToSearch.equals(entry.getValue().get(index).getFirstName())) {
					System.out.println("Address Book name: " + entry.getKey());
					System.out.println("Person Name: " + entry.getValue().get(index).getFirstName());
					System.out.println("State: " + entry.getValue().get(index).getAddress().getState());
					System.out.println("City: " + entry.getValue().get(index).getAddress().getCity());
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
		List<Contacts> contactArray = addressBook.get(bookName);
		List<Contacts> sortedNameList = contactArray.stream()
				.sorted((s1, s2) -> s1.getFirstName().compareTo(s2.getFirstName())).collect(Collectors.toList());
		System.out.println("Persons in the AddressBook " + bookName + " in sorted order: ");
		sortedNameList.stream().forEach(n -> System.out.println(n.getFirstName() + " " + n.getLastName()));
	}

	@Override
	public void sortByCity(String nameOfCity) {
		List<Contacts> contactArray = personsInCity.get(nameOfCity);
		List<Contacts> sortedCityList = contactArray.stream()
				.sorted((s1, s2) -> (s1.getAddress().getCity().compareTo(s2.getAddress().getCity())))
				.collect(Collectors.toList());
		System.out.println("Persons in the City " + nameOfCity + " in sorted order: ");
		sortedCityList.stream().forEach(n -> System.out.println(n.getFirstName() + " " + n.getLastName()));
	}

	@Override
	public void sortByState(String nameOfState) {
		List<Contacts> contactArray = personsInState.get(nameOfState);
		List<Contacts> sortedStateList = contactArray.stream()
				.sorted((s1, s2) -> (s1.getAddress().getState().compareTo(s2.getAddress().getState())))
				.collect(Collectors.toList());
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
	public List<Contacts> getContactFromDateRange(String date) throws AddressBookException {
		List<Contacts> contactList = addressBookDBService.getContactFromDateRange(date);
		return contactList;
	}

	@Override
	public List<Contacts> getContactFromAddress(String city, String state) throws AddressBookException {
		List<Contacts> contactList = addressBookDBService.getContactFromAddress(city, state);
		return contactList;
	}

	@Override
	public void addContactToDataBase(Contacts contacts, Address address, String addressBookName)
			throws AddressBookException {
		addressBookList.add(addressBookDBService.addContact(contacts, address, addressBookName));

	}

}
