package com.contact.db.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.contact.db.exception.NotFoundException;
import com.contact.db.exception.ServerException;
import com.contact.db.model.CustomData;
import com.contact.db.model.CustomField;
import com.contact.db.model.CustomFieldType;
import com.contact.db.service.ContactService;
import com.contact.db.service.CustomFieldService;
import com.contact.db.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value="/v1/api")
@Api(value="Custom Fields", description="Operations Pertaining to Custom Fields")
public class FieldController {

@Autowired
private CustomFieldService customFieldService;

@Autowired
private ContactService contactService;

@ApiOperation(value="Create Custom Field")
@ResponseStatus(code=HttpStatus.CREATED)
@ApiResponses(value={
		@ApiResponse(code=201, message="Successfully created custom field"),
		@ApiResponse(code=401, message=Constants.GENERIC_NOT_AUTHORIZED),
		@ApiResponse(code=403, message=Constants.GENERIC_FORBIDDEN),
		@ApiResponse(code=404, message=Constants.GENERIC_NOT_FOUND),
		@ApiResponse(code=500, message=Constants.GENERIC_SERVER_ERROR)
})
@RequestMapping(value="/custom-field", method=RequestMethod.POST)
public ResponseEntity<String> createCustomField( @RequestBody CustomField customField) {
	if(customFieldService.saveCustomField(customField)) {
		return new ResponseEntity<String>("Successfully created custom field", HttpStatus.CREATED);
	}
	throw new ServerException("Error creating custom field");
}

@ApiOperation(value="Create Custom Data")
@ResponseStatus(code=HttpStatus.CREATED)
@ApiResponses(value={
		@ApiResponse(code=201, message="Successfully created custom data"),
		@ApiResponse(code=401, message=Constants.GENERIC_NOT_AUTHORIZED),
		@ApiResponse(code=403, message=Constants.GENERIC_FORBIDDEN),
		@ApiResponse(code=404, message=Constants.GENERIC_NOT_FOUND),
		@ApiResponse(code=500, message=Constants.GENERIC_SERVER_ERROR)
})
@RequestMapping(value="/custom-data", method=RequestMethod.POST)
public ResponseEntity<String> createCustomData( @RequestBody CustomData customData) {
	if(!contactService.contactExists(customData.getContactId())) {
		throw new NotFoundException("Contact not found");
	}
	
	if(!customFieldService.customFieldExists(customData.getFieldId())) {
		throw new NotFoundException("Field not found");
	}
	
	if(customFieldService.saveCustomData(customData)) {
		return new ResponseEntity<String>("Successfully created custom data", HttpStatus.CREATED);
	}
	throw new ServerException("Error creating custom data");
}
	
@ApiOperation(value="Get Custom Fields")
@ApiResponses(value={
		@ApiResponse(code=200, message="Successfully retrieved list"),
		@ApiResponse(code=401, message=Constants.GENERIC_NOT_AUTHORIZED),
		@ApiResponse(code=403, message=Constants.GENERIC_FORBIDDEN),
		@ApiResponse(code=404, message=Constants.GENERIC_NOT_FOUND),
		@ApiResponse(code=500, message=Constants.GENERIC_SERVER_ERROR)
})
@RequestMapping(value="/custom-fields", method=RequestMethod.GET)
public ResponseEntity<List<CustomField>> getCustomFields() {
	return new ResponseEntity<List<CustomField>>(customFieldService.getAllCustomFields(), HttpStatus.OK);
}

@ApiOperation(value="Get Custom Data By Contact")
@ApiResponses(value={
		@ApiResponse(code=200, message="Successfully retrieved list"),
		@ApiResponse(code=401, message=Constants.GENERIC_NOT_AUTHORIZED),
		@ApiResponse(code=403, message=Constants.GENERIC_FORBIDDEN),
		@ApiResponse(code=404, message=Constants.GENERIC_NOT_FOUND),
		@ApiResponse(code=500, message=Constants.GENERIC_SERVER_ERROR)
})
@RequestMapping(value="/custom-data/{id}", method=RequestMethod.GET)
public ResponseEntity<Map> getCustomDataByContact(@PathVariable(value="id") Long id) {
	if(!contactService.contactExists(id)) {
		throw new NotFoundException("Contact not found");
	}
	
	return new ResponseEntity<Map>(customFieldService.getCustomDataByContactId(id), HttpStatus.OK);
}

}
