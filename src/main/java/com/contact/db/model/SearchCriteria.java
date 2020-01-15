package com.contact.db.model;

import com.fasterxml.jackson.annotation.JsonInclude;

public class SearchCriteria {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String firstName;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String lastName;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String email;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String phone;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Address address;
	
	public SearchCriteria() {}
	
	public SearchCriteria(String firstName, String lastName, String email, String phone, Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
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
	
}
