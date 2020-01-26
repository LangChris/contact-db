package com.contact.db.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.contact.db.exception.BadRequestException;
import com.contact.db.exception.ServerException;
import com.contact.db.model.ColumnType;
import com.contact.db.model.Contact;
import com.contact.db.model.SearchCriteria;
import com.contact.db.model.dto.ContactDto;
import com.contact.db.repository.DatabaseOperations;
import com.contact.db.service.ContactService;
import com.contact.db.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = {"https://langchris.github.io", "http://localhost:4200"})
@RequestMapping(value="/v1/api")
@Api(value="Contact DB", description="Operations Pertaining to Contacts")
public class ContactController {

@Autowired
private ContactService contactService;

@Autowired
private DatabaseOperations databaseOperations;

@ApiOperation(value="Get All Contacts")
@ApiResponses(value={
		@ApiResponse(code=200, message="Successfully retrieved list"),
		@ApiResponse(code=401, message=Constants.GENERIC_NOT_AUTHORIZED),
		@ApiResponse(code=403, message=Constants.GENERIC_FORBIDDEN),
		@ApiResponse(code=404, message=Constants.GENERIC_NOT_FOUND),
		@ApiResponse(code=500, message=Constants.GENERIC_SERVER_ERROR)
})
@RequestMapping(value="/contacts", method=RequestMethod.GET, produces="application/json")
public ResponseEntity<List<ContactDto>> getContacts(
		@RequestParam(required=false) String sortBy, 
		@RequestParam(required=false) String direction,
		@RequestParam(required=false) boolean customFields) {
	if(direction != null && !direction.toLowerCase().equals("asc") && !direction.toLowerCase().equals("desc") ) {
		throw new BadRequestException("direction must be either asc or desc");
	}
	if(customFields) {
		return new ResponseEntity<List<ContactDto>>(contactService.findAllDtosWithCustomData(sortBy, direction), HttpStatus.OK);
	} else {
		return new ResponseEntity<List<ContactDto>>(contactService.findAllDtos(sortBy, direction), HttpStatus.OK);
	}
}

@ApiOperation(value="Search Contacts")
@ApiResponses(value={
		@ApiResponse(code=200, message="Successfully retrieved list"),
		@ApiResponse(code=401, message=Constants.GENERIC_NOT_AUTHORIZED),
		@ApiResponse(code=403, message=Constants.GENERIC_FORBIDDEN),
		@ApiResponse(code=404, message=Constants.GENERIC_NOT_FOUND),
		@ApiResponse(code=500, message=Constants.GENERIC_SERVER_ERROR)
})
@RequestMapping(value="/filtered-contacts", method=RequestMethod.POST, produces="application/json")
public ResponseEntity<List<ContactDto>> getFilteredContacts(
		@RequestParam(required=false) String sortBy, 
		@RequestParam(required=false) String direction,
		@RequestParam(required=false) boolean customFields,
		@RequestBody(required=false) SearchCriteria searchCriteria ) {
	
	if(direction != null && !direction.toLowerCase().equals("asc") && !direction.toLowerCase().equals("desc") ) {
		throw new BadRequestException("direction must be either asc or desc");
	}
	
	List<ContactDto> contacts = new ArrayList<>();
	
	if(customFields) {
		contacts = contactService.filterResults(contactService.findAllDtosWithCustomData(sortBy, direction), searchCriteria);
	} else {
		contacts = contactService.filterResults(contactService.findAllDtos(sortBy, direction), searchCriteria);
	}
	return new ResponseEntity<List<ContactDto>>(contacts, HttpStatus.OK);
}

@ApiOperation(value="Get Contact by Id")
@ApiResponses(value={
		@ApiResponse(code=200, message="Successfully retrieved coontact"),
		@ApiResponse(code=401, message=Constants.GENERIC_NOT_AUTHORIZED),
		@ApiResponse(code=403, message=Constants.GENERIC_FORBIDDEN),
		@ApiResponse(code=404, message=Constants.GENERIC_NOT_FOUND),
		@ApiResponse(code=500, message=Constants.GENERIC_SERVER_ERROR)
})
@RequestMapping(value="/contact/{id}", method=RequestMethod.GET, produces="application/json")
public ResponseEntity<ContactDto> getContact(
		@PathVariable(value="id") Long id,
		@RequestParam(required=false) boolean customFields) {
	if(customFields) {
		return new ResponseEntity<ContactDto>(contactService.findDtoWithCustomData(id), HttpStatus.OK);
	} else {
		return new ResponseEntity<ContactDto>(new ContactDto(contactService.findByContactId(id)), HttpStatus.OK);
	}
}

@ApiOperation(value="Create Contact")
@ResponseStatus(code=HttpStatus.CREATED)
@ApiResponses(value={
		@ApiResponse(code=201, message="Successfully created entity"),
		@ApiResponse(code=401, message=Constants.GENERIC_NOT_AUTHORIZED),
		@ApiResponse(code=403, message=Constants.GENERIC_FORBIDDEN),
		@ApiResponse(code=404, message=Constants.GENERIC_NOT_FOUND),
		@ApiResponse(code=500, message=Constants.GENERIC_SERVER_ERROR)
})
@RequestMapping(value="/contact", method=RequestMethod.POST, produces="application/json")
public ResponseEntity<ContactDto> createContact( @RequestBody Contact contact) {
	return new ResponseEntity<ContactDto>(new ContactDto(contactService.saveContact(contact)), HttpStatus.CREATED);
}

@ApiOperation(value="Import Contacts")
@ResponseStatus(code=HttpStatus.CREATED)
@ApiResponses(value={
		@ApiResponse(code=201, message="Successfully imported contacts"),
		@ApiResponse(code=401, message=Constants.GENERIC_NOT_AUTHORIZED),
		@ApiResponse(code=403, message=Constants.GENERIC_FORBIDDEN),
		@ApiResponse(code=404, message=Constants.GENERIC_NOT_FOUND),
		@ApiResponse(code=500, message=Constants.GENERIC_SERVER_ERROR)
})
@RequestMapping(value="/import-contacts", method=RequestMethod.POST)
public ResponseEntity<String> importContacts( @RequestParam("file") MultipartFile file) {
	if (file.isEmpty()) {
		throw new BadRequestException("Please select a file to upload");
    }
	String extension = file.getOriginalFilename()
			.substring(file.getOriginalFilename().indexOf(".") + 1)
			.toLowerCase();
	System.out.println(extension);
	if(!extension.equals("csv") || !extension.equals("xlsx")) {
		throw new BadRequestException("Invalid file type must be CSV or XLSX");
	}
	
	return new ResponseEntity<String>("Successfully imported contacts", HttpStatus.CREATED);
}

@ApiOperation(value="Create New Column", hidden=true)
@ResponseStatus(code=HttpStatus.CREATED)
@ApiResponses(value={
		@ApiResponse(code=201, message="Successfully created column"),
		@ApiResponse(code=401, message=Constants.GENERIC_NOT_AUTHORIZED),
		@ApiResponse(code=403, message=Constants.GENERIC_FORBIDDEN),
		@ApiResponse(code=404, message=Constants.GENERIC_NOT_FOUND),
		@ApiResponse(code=500, message=Constants.GENERIC_SERVER_ERROR)
})
@RequestMapping(value="/column", method=RequestMethod.POST)
public ResponseEntity<String> createColumn( @RequestParam String name, @RequestParam ColumnType type, @RequestParam(required=false) Integer length) {
	
	if(databaseOperations.addNewColumn("contactdb", "contact", name, type, length)) {
		return new ResponseEntity<String>("Successfully created column", HttpStatus.CREATED);
	}
	throw new ServerException("Error creating column");
}
	
}
