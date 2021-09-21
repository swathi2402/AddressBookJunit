package com.bridgelabz.addressbook;

import java.util.Scanner;

import com.bridgelabz.addressbook.ContactOperationsImpl.I0Service;

public class AddressBookMain {

	public static void main(String[] args) {
		System.out.println("***** Welcome to Address Book Program *****");

		ContactOperationsIF contactOperations = new ContactOperationsImpl();
		Scanner scanner = new Scanner(System.in);

		boolean exitAddressBook = false;

		while (!exitAddressBook) {
			System.out.println(
					"Press:\n1 to Add new AddressBook \n2 to Search a Person \n3 to get persons by city and state \n4 to get count \n5 to display sort by name \n6 to sort by city \n7 to Exit");

			switch (scanner.nextInt()) {
			case 1:
				System.out.println("Enter AddressBook Name");
				String addressBookName = scanner.next();
				contactOperations.addAddressBook(addressBookName);

				boolean exitContact = false;
				while (!exitContact) {
					System.out.println("Press:\n1 to Add contact \n2 to Edit contact \n3 to Delete \n4 to Exit");

					switch (scanner.nextInt()) {
					case 1:
						contactOperations.writeData(I0Service.CONSOLE_IO, addressBookName);
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
				contactOperations.searchPerson(scanner.next());
				break;

			case 3:
				System.out.println("Enter name of the city");
				contactOperations.getPersonsInCity(scanner.next());

				System.out.println("Enter name of the state");
				contactOperations.getPersonsInState(scanner.next());
				break;

			case 4:
				System.out.println("Enter name of the city");
				contactOperations.getCountInCity(scanner.next());

				System.out.println("Enter name of the state");
				contactOperations.getCountInState(scanner.next());
				break;

			case 5:
				System.out.println("Enter name of the AddressBook");
				contactOperations.sortByName(scanner.next());
				break;

			case 6:
				System.out.println("Enter name of the city");
				contactOperations.sortByCity(scanner.next());

				System.out.println("Enter name of the state");
				contactOperations.sortByState(scanner.next());
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
