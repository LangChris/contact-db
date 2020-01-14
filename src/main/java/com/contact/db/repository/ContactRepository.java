package com.contact.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.contact.db.model.Contact;

public interface ContactRepository extends CrudRepository<Contact, Long> {

	Contact findByContactId(Long contactId);
	
	List<Contact> findByFirstName(String firstName);
	List<Contact> findByLastName(String lastName);
	
	List<Contact> findAllByOrderByLastNameAsc();
	List<Contact> findAllByOrderByLastNameDesc();
	
	List<Contact> findAllByOrderByFirstNameAsc();
	List<Contact> findAllByOrderByFirstNameDesc();
	
	List<Contact> findAllByOrderByEmailAsc();
	List<Contact> findAllByOrderByEmailDesc();
	
}
