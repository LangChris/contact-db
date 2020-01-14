package com.contact.db.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.contact.db.exception.ServerException;
import com.contact.db.model.Contact;
import com.contact.db.model.CustomData;
import com.contact.db.model.CustomFieldType;
import com.contact.db.model.SearchCriteria;
import com.contact.db.model.dto.ContactDto;
import com.contact.db.repository.ContactRepository;
import com.contact.db.util.Constants;

@Service
public class ContactService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private int CRITERIA_COUNT = 0;
	private List<ContactDto> FILTERED_RESULTS = new ArrayList<>();

	@Autowired
	private ContactRepository contactRepo;
	
	@Autowired CustomFieldService customFieldService;

	public Contact saveContact(Contact contact) {
		try {
			return contactRepo.save(contact);
		} catch(DataIntegrityViolationException dive) {
			throw new ServerException(Constants.GENERIC_SERVER_ERROR);
		}
	}

	public Contact findByContactId(Long contactId) {
		return contactRepo.findByContactId(contactId);
	}
	
	public boolean contactExists(Long contactId) {
		return contactRepo.existsById(contactId);
	}

	public List<Contact> findAll() {
		return (List<Contact>) contactRepo.findAll();
	}
	
	public List<ContactDto> findAllDtos(String sortBy, String direction) {
		List<Contact> contacts = new ArrayList<>();
		if(sortBy == null) {
			sortBy = "last_name";
		}
		
		if(direction == null) {
			direction = "asc";
		}
		
		if(sortBy.toLowerCase().equals("last_name")) {
			if(direction.toLowerCase().equals("asc")) {
				contacts = contactRepo.findAllByOrderByLastNameAsc();
			} else if(direction.toLowerCase().equals("desc")) {
				contacts = contactRepo.findAllByOrderByLastNameDesc();
			}
		}
		else if(sortBy.toLowerCase().equals("first_name")) {
			if(direction.toLowerCase().equals("asc")) {
				contacts = contactRepo.findAllByOrderByFirstNameAsc();
			} else if(direction.toLowerCase().equals("desc")) {
				contacts = contactRepo.findAllByOrderByFirstNameDesc();
			}
		}
		else if(sortBy.toLowerCase().equals("email")) {
			if(direction.toLowerCase().equals("asc")) {
				contacts = contactRepo.findAllByOrderByEmailAsc();
			} else if(direction.toLowerCase().equals("desc")) {
				contacts = contactRepo.findAllByOrderByEmailDesc();
			}
		}
		
		List<ContactDto> contactDtos = new ArrayList<>();
		for(Contact contact : contacts) {
			contactDtos.add(new ContactDto(contact));
		}
		
		return contactDtos;
	}
	
	public List<ContactDto> findAllDtosWithCustomData(String sortBy, String direction) {
		List<Contact> contacts = new ArrayList<>();
		if(sortBy == null) {
			sortBy = "last_name";
		}
		
		if(direction == null) {
			direction = "asc";
		}
		
		if(sortBy.toLowerCase().equals("last_name")) {
			if(direction.toLowerCase().equals("asc")) {
				contacts = contactRepo.findAllByOrderByLastNameAsc();
			} else if(direction.toLowerCase().equals("desc")) {
				contacts = contactRepo.findAllByOrderByLastNameDesc();
			}
		}
		else if(sortBy.toLowerCase().equals("first_name")) {
			if(direction.toLowerCase().equals("asc")) {
				contacts = contactRepo.findAllByOrderByFirstNameAsc();
			} else if(direction.toLowerCase().equals("desc")) {
				contacts = contactRepo.findAllByOrderByFirstNameDesc();
			}
		}
		List<ContactDto> contactDtos = new ArrayList<>();
		
		for(int i=0; i < contacts.size(); i++) {
			List<CustomData> customFields = customFieldService.getCustomFieldsByContactId(contacts.get(i).getContactId());

			Map customData = new HashMap<>();
			for(CustomData customField: customFields) {
				String name = customFieldService.getFieldById(customField.getFieldId()).getName();
				CustomFieldType type = customFieldService.getFieldById(customField.getFieldId()).getType();
				
				switch(type) {
				case STRING: 
				case STRING_LIST:
					String stringVal = customField.getValue();
					customData.put(name, stringVal);
					break;
				case INT:
				case INT_LIST:
					int intVal = Integer.parseInt(customField.getValue());
					customData.put(name, intVal);
					break;
				case BOOLEAN:
					boolean boolVal = Boolean.valueOf(customField.getValue());
					customData.put(name, boolVal);
					break;
				default:	
				}
			}
			contactDtos.add(new ContactDto(contacts.get(i), customData));
		}
		
		return contactDtos;
	}

	public List<ContactDto> filterResults(List<ContactDto> contacts, SearchCriteria searchCriteria) {
		FILTERED_RESULTS = contacts;
		
		if(		searchCriteria.getFirstName() == null &&
				searchCriteria.getLastName() == null &&
				searchCriteria.getEmail() == null &&
				searchCriteria.getPhone() == null &&
				searchCriteria.getAddress() == null) {
			
			logger.info("no search criteria provided.. returning all contacts"); 
			return FILTERED_RESULTS; 
		}
		
		CRITERIA_COUNT = 0;
		
		if(!Objects.isNull(searchCriteria.getFirstName())) {
			filterByFirstName(searchCriteria); 
		}
		
		if(!Objects.isNull(searchCriteria.getLastName())) {
			filterByLastName(searchCriteria); 
		}
		
		if(!Objects.isNull(searchCriteria.getEmail())) {
			filterByEmail(searchCriteria); 
		}
		
		if(!Objects.isNull(searchCriteria.getPhone())) {
			filterByPhone(searchCriteria); 
		}
		
		if(!Objects.isNull(searchCriteria.getAddress())) {
			filterByAddress(searchCriteria); 
		}
		
		logger.info("Found {} search criteria", CRITERIA_COUNT);
		logger.info("Found {} results", FILTERED_RESULTS.size());
		return FILTERED_RESULTS;
	}
	
	private void filterByFirstName(SearchCriteria searchCriteria) {
		if(!StringUtils.isEmpty(searchCriteria.getFirstName())) {
			// Filter By First Name
			CRITERIA_COUNT++;
			List<ContactDto> results = new ArrayList<>();

			FILTERED_RESULTS 
			.stream() 
			.filter(c ->
			StringUtils.isEmpty(c.getFirstName()) ||
			!c.getFirstName().toLowerCase().contains(searchCriteria.getFirstName().toLowerCase()))
			.collect(Collectors.toCollection(() -> results));
			FILTERED_RESULTS.removeAll(results);
		}
	}
	
	private void filterByLastName(SearchCriteria searchCriteria) {
		if(!StringUtils.isEmpty(searchCriteria.getLastName())) {
			// Filter By Last Name
			CRITERIA_COUNT++;
			List<ContactDto> results = new ArrayList<>();

			FILTERED_RESULTS 
			.stream() 
			.filter(c ->
			StringUtils.isEmpty(c.getLastName()) ||
			!c.getLastName().toLowerCase().contains(searchCriteria.getLastName().toLowerCase()))
			.collect(Collectors.toCollection(() -> results));
			FILTERED_RESULTS.removeAll(results);
		}
	}
	
	private void filterByEmail(SearchCriteria searchCriteria) {
		if(!StringUtils.isEmpty(searchCriteria.getEmail())) {
			// Filter By Email
			CRITERIA_COUNT++;
			List<ContactDto> results = new ArrayList<>();

			FILTERED_RESULTS 
			.stream() 
			.filter(c ->
			StringUtils.isEmpty(c.getEmail()) ||
			!c.getEmail().toLowerCase().contains(searchCriteria.getEmail().toLowerCase()))
			.collect(Collectors.toCollection(() -> results));
			FILTERED_RESULTS.removeAll(results);
		}
	}
	
	private void filterByPhone(SearchCriteria searchCriteria) {
		if(!StringUtils.isEmpty(searchCriteria.getPhone())) {
			// Filter By Phone
			CRITERIA_COUNT++;
			List<ContactDto> results = new ArrayList<>();

			FILTERED_RESULTS 
			.stream() 
			.filter(c ->
			StringUtils.isEmpty(c.getPhone()) ||
			!c.getPhone().toLowerCase().contains(searchCriteria.getPhone().toLowerCase()))
			.collect(Collectors.toCollection(() -> results));
			FILTERED_RESULTS.removeAll(results);
		}
	}

	private void filterByAddress(SearchCriteria searchCriteria) {
		if(!StringUtils.isEmpty(searchCriteria.getAddress().getAddressLine())) {
			// Filter By Address Line
			CRITERIA_COUNT++;
			List<ContactDto> results = new ArrayList<>();

			FILTERED_RESULTS 
			.stream() 
			.filter(c ->
			Objects.isNull(c.getAddress()) ||
			StringUtils.isEmpty(c.getAddress().getAddressLine()) ||
			!c.getAddress().getAddressLine().toLowerCase().contains(searchCriteria.getAddress().getAddressLine().toLowerCase()))
			.collect(Collectors.toCollection(() -> results));
			FILTERED_RESULTS.removeAll(results);
		}

		if(!StringUtils.isEmpty(searchCriteria.getAddress().getCity())) {
			// Filter By Address City
			CRITERIA_COUNT++;
			List<ContactDto> results = new ArrayList<>();

			FILTERED_RESULTS 
			.stream() 
			.filter(c ->
			Objects.isNull(c.getAddress()) ||
			StringUtils.isEmpty(c.getAddress().getCity()) ||
			!c.getAddress().getCity().toLowerCase().contains(searchCriteria.getAddress().getCity().toLowerCase()))
			.collect(Collectors.toCollection(() -> results));
			FILTERED_RESULTS.removeAll(results);
		}

		if(!StringUtils.isEmpty(searchCriteria.getAddress().getState())) {
			// Filter By Address State
			CRITERIA_COUNT++;
			List<ContactDto> results = new ArrayList<>();

			FILTERED_RESULTS 
			.stream() 
			.filter(c ->
			Objects.isNull(c.getAddress()) ||
			StringUtils.isEmpty(c.getAddress().getState()) ||
			!c.getAddress().getState().toLowerCase().contains(searchCriteria.getAddress().getState().toLowerCase()))
			.collect(Collectors.toCollection(() -> results));
			FILTERED_RESULTS.removeAll(results);
		}

		if(searchCriteria.getAddress().getZip() != 0) {
			// Filter By Address Zip
			CRITERIA_COUNT++;
			List<ContactDto> results = new ArrayList<>();

			FILTERED_RESULTS 
			.stream() 
			.filter(c ->
			Objects.isNull(c.getAddress()) ||
			StringUtils.isEmpty(c.getAddress().getZip()) ||
			c.getAddress().getZip() != searchCriteria.getAddress().getZip())
			.collect(Collectors.toCollection(() -> results));
			FILTERED_RESULTS.removeAll(results);
		}
	}

}
