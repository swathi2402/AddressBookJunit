package com.bridgelabz.addressbook;

import java.util.Scanner;

public class AddressBookMain {

	public static void main(String[] args) {
		System.out.println("***** Welcome to Address Book Program *****");

		ContactOperationsIF contactOperations = new ContactOperationsImpl();
		Scanner scanner = new Scanner(System.in);

		boolean exitAddressBook = false;

		while (!exitAddressBook) {
			System.out.println(
					"Press:\n1 to Add new AddressBook \n2 to Search a Person \n3 to get persons by city and state \n4 to get count \n5 to display sort by name \n6 to sort by city \n7 to Exit");
			int options = scanner.nextInt();

			switch (options) {
			case 1:
				System.out.println("Enter AddressBook Name");
				String addressBookName = scanner.next();
				contactOperations.addAddressBook(addressBookName);

				boolean exitContact = false;
				while (!exitContact) {
					System.out.println("Press:\n1 to Add contact \n2 to Edit contact \n3 to Delete \n4 to Exit");
					int optionSelected = scanner.nextInt();

					switch (optionSelected) {
					case 1:
						contactOperations.addContact(addressBookName);
						break;
					case 2:
						contactOperations.checkToEdit(addressBookName);
						break;
					case 3:
						contactOperations.deleteContact(addressBookName);
						break;
					case 4:
						System.out.println("Exiting Contacts");
						exitContact = true;
						break;
					}
				}
				break;

			case 2:
				System.out.println("Enter first name of the person to search");
				String nameToSearch = scanner.next();
				contactOperations.searchPerson(nameToSearch);
				break;

			case 3:
				System.out.println("Enter name of the city");
				String city = scanner.next();
				contactOperations.getPersonsInCity(city);

				System.out.println("Enter name of the state");
				String state = scanner.next();
				contactOperations.getPersonsInState(state);
				break;

			case 4:
				System.out.println("Enter name of the city");
				String cityName = scanner.next();
				contactOperations.getCountInCity(cityName);

				System.out.println("Enter name of the state");
				String stateName = scanner.next();
				contactOperations.getCountInState(stateName);
				break;

			case 5:
				System.out.println("Enter name of the AddressBook");
				String bookName = scanner.next();
				contactOperations.sortByName(bookName);
				break;

			case 6:
				System.out.println("Enter name of the city");
				String nameOfCity = scanner.next();
				contactOperations.sortByCity(nameOfCity);

				System.out.println("Enter name of the state");
				String nameOfState = scanner.next();
				contactOperations.sortByState(nameOfState);
				break;

			case 7:
				System.out.println("Exiting Address Book");
				exitAddressBook = true;
				scanner.close();
				break;
			}

		}
	}
}
