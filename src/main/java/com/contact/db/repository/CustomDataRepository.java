package com.contact.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.contact.db.model.CustomData;

public interface CustomDataRepository extends CrudRepository<CustomData, Long> {

	CustomData findByContactIdAndFieldId(Long contactId, Long fieldId);
	
	List<CustomData> findByContactId(Long contactId);
	
}
