package com.bridgelabz.addressbook;

import java.sql.SQLException;

public class AddressBookException extends SQLException {

	enum ExceptionType {
		UNKOWN_DATABASE, SQL_EXCEPTION
	}

	ExceptionType type;

	public AddressBookException(ExceptionType type, String message) {
		super(message);
		this.type = type;
	}
}
