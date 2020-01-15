package com.contact.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonInclude;

@Embeddable
public class Address implements Serializable {

	private static final long serialVersionUID = -6L;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Column(name="address_line", length=200)
	private String addressLine;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Column(name="city", length=50)
	private String city;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Column(name="state", length=2)
	private String state;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Column(name="zip", length=5)
	private int zip;
	
	public Address() {}
	
	public Address(String addressLine, String city, String state, int zip) {
		this.addressLine = addressLine;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}
	
	@Override
	public String toString() {
		return String.format(
				"Address={addressLine: %s, city: %s, state: %s, zip: %s}", 
				addressLine, city, state, String.valueOf(zip));
	}
}
