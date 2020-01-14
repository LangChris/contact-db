package com.contact.db.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.contact.db.exception.ServerException;
import com.contact.db.model.CustomData;
import com.contact.db.model.CustomField;
import com.contact.db.model.CustomFieldType;
import com.contact.db.repository.CustomDataRepository;
import com.contact.db.repository.CustomFieldRepository;
import com.contact.db.util.Constants;

@Service
public class CustomFieldService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CustomFieldRepository customFieldRepo;
	
	@Autowired
	private CustomDataRepository customDataRepo;

	public boolean saveCustomField(CustomField customField) {
		try {
			customField.setName(
					customField.getName()
					.toLowerCase()
					.trim()
					.replace(" ", "_"));
			customFieldRepo.save(customField);
			return true;
		} catch(DataIntegrityViolationException dive) {
			throw new ServerException(Constants.GENERIC_SERVER_ERROR);
		}
	}
	
	public boolean saveCustomData(CustomData customData) {
		try {
			customDataRepo.save(customData);
			return true;
		} catch(DataIntegrityViolationException dive) {
			throw new ServerException(Constants.GENERIC_SERVER_ERROR);
		}
	}
	
	public List<CustomData> getCustomFieldsByContactId(Long contactId) {
		return customDataRepo.findByContactId(contactId);
	}
	
	public Map getCustomDataByContactId(Long contactId) {
		List<CustomData> customFields = customDataRepo.findByContactId(contactId);
		Map customData = new HashMap<>();
		for(CustomData customField : customFields) {
			String name = getFieldById(customField.getFieldId()).getName();
			CustomFieldType type = getFieldById(customField.getFieldId()).getType();
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
		return customData;
	}
	
	public List<CustomField> getAllCustomFields() {
		return (List<CustomField>)customFieldRepo.findAll();
	}
	
	public boolean customFieldExists(Long fieldId) {
		return customFieldRepo.existsById(fieldId);
	}
	
	public CustomField getFieldById(Long fieldId) {
		return customFieldRepo.findByFieldId(fieldId);
	}
	
}
