package com.contact.db.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="custom_data")
public class CustomData implements Serializable {

	private static final long serialVersionUID = -6L;
	
	@Id
	@Column(name="data_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long dataId;
	
	@Column(name = "field_id")
	private Long fieldId;
	
	@Column(name = "contact_id")
	private Long contactId;
	
	@Column(name = "value")
	private String value;

	public CustomData() {}
	
	public CustomData(Long fieldId, Long contactId, String value) {
		super();
		this.fieldId = fieldId;
		this.contactId = contactId;
		this.value = value;
	}

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
