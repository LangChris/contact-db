package com.contact.db.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="custom_field")
public class CustomField implements Serializable {

	private static final long serialVersionUID = -6L;
	
	@Id
	@Column(name="field_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long fieldId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "value")
	private String value;
	
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private CustomFieldType type;

	public CustomField() {}
	
	public CustomField(String name, String value, CustomFieldType type) {
		super();
		this.name = name;
		this.value = value;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public CustomFieldType getType() {
		return type;
	}

	public void setType(CustomFieldType type) {
		this.type = type;
	}
	
}
