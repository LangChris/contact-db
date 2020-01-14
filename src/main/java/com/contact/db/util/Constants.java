package com.contact.db.util;

import org.springframework.stereotype.Service;

@Service
public class Constants {

	// Generic Response Messages
	public static final String GENERIC_NOT_AUTHORIZED = "You are not authorized to view the resource";
	public static final String GENERIC_FORBIDDEN = "Access to the resource is forbidden";
	public static final String GENERIC_NOT_FOUND = "The resource you were trying to reach is not found";
	public static final String GENERIC_SERVER_ERROR = "An internal server error occured while trying to view the resource";

}
