package com.bridgelabz.addressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ContactOperationsImpl implements ContactOperationsIF {

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
	public void addContact(String addressBookName) {

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

		boolean isPresent = false;

		for (int index = 0; index < addressBook.get(addressBookName).size(); index++) {
			if (firstName.equals(addressBook.get(addressBookName).get(index).getFirstName())) {
				System.out.println("Contact for " + firstName + " " + " is already exists");
				isPresent = true;
				break;
			}
		}

		if (!isPresent) {
			Contact newContact = new Contact(firstName, lastName, address, city, state, ZIP, phoneNumber, email);
			addressBook.get(addressBookName).add(newContact);
			System.out.println("Contact for " + firstName + " " + lastName + " is added");

			if (personsInCity.get(city) == null) {
				personsInCity.put(city, new ArrayList<Contact>());
			}
			personsInCity.get(city).add(newContact);

			if (personsInState.get(state) == null) {
				personsInState.put(state, new ArrayList<Contact>());
			}
			personsInState.get(state).add(newContact);

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
}
