package com.contact.db.model.dto;

import java.util.Date;
import java.util.Map;

import com.contact.db.model.Address;
import com.contact.db.model.Contact;
import com.fasterxml.jackson.annotation.JsonInclude;

public class ContactDto {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private Address address;
	private Date createdDate;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Map customData;
	
	public ContactDto() {}
	
	public ContactDto(Contact contact) {
		this.id = contact.getContactId();
		this.firstName = contact.getFirstName();
		this.lastName = contact.getLastName();
		this.email = contact.getEmail();
		this.phone = contact.getPhone();
		this.address = contact.getAddress();
		this.createdDate = contact.getCreatedDate();
	}
	
	public ContactDto(Contact contact, Map customData) {
		this.id = contact.getContactId();
		this.firstName = contact.getFirstName();
		this.lastName = contact.getLastName();
		this.email = contact.getEmail();
		this.phone = contact.getPhone();
		this.address = contact.getAddress();
		this.createdDate = contact.getCreatedDate();
		this.customData = customData;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setCustomData(Map customData) {
		this.customData = customData;
	}
	
	public Map getCustomData() {
		return customData;
	}

	@Override
	public String toString() {
		return String.format(
				"ContactDto={id: %d, firstName: %s, lastName: %s, email: %s, phone: %s, address: %s, createdDate: %s}", 
				this.id,
				this.firstName,
				this.lastName,
				this.email,
				this.phone,
				this.address.toString(),
				this.createdDate.toString());
	}
}
