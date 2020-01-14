package com.contact.db.repository;

import org.springframework.data.repository.CrudRepository;
import com.contact.db.model.CustomField;

public interface CustomFieldRepository extends CrudRepository<CustomField, Long> {

	CustomField findByFieldId(Long fieldId);
	
	CustomField findByName(String name);
}
