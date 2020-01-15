package com.contact.db.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.contact.db.model.CustomData;
import com.contact.db.model.CustomField;
import com.contact.db.model.CustomFieldType;
import com.contact.db.service.ContactService;
import com.contact.db.service.CustomFieldService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(FieldController.class)
public class FieldControllerTests {

	private final String baseUrl = "http://localhost:8080/v1/api";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ContactService contactService;
	
	@MockBean
	private CustomFieldService customFieldService;
	
	private CustomField customField1;
	private CustomField customField2;
	private CustomField customField3;
	
	private CustomData customData1;
	
	@Before
	public void init() {
		customField1 = new CustomField("employer", null, CustomFieldType.STRING);
		customField2 = new CustomField("yes-no", "YES,NO", CustomFieldType.STRING_LIST);
		customField3 = new CustomField("age", null, CustomFieldType.INT);
		
		customData1 = new CustomData(1L, 1L, "Octo Consulting Group");
	}
	
	@Test
	public void createCustomField_Success() throws Exception {
		String url = baseUrl + "/custom-field";
		
		Mockito
		.when(customFieldService.saveCustomField(Mockito.any()))
		.thenReturn(true);
		
		MvcResult result = mockMvc.perform(post(url)
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(customField1)))
						.andReturn();
				
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void createCustomField_Failed() throws Exception {
		String url = baseUrl + "/custom-field";
		
		Mockito
		.when(customFieldService.saveCustomField(Mockito.any()))
		.thenReturn(false);
		
		MvcResult result = mockMvc.perform(post(url)
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(customField1)))
						.andReturn();
				
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void createCustomData_Success() throws Exception {
		String url = baseUrl + "/custom-data";
		
		Mockito
		.when(contactService.contactExists(Mockito.any()))
		.thenReturn(true);
		
		Mockito
		.when(customFieldService.customFieldExists(Mockito.any()))
		.thenReturn(true);
		
		Mockito
		.when(customFieldService.saveCustomData(Mockito.any()))
		.thenReturn(true);
		
		MvcResult result = mockMvc.perform(post(url)
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(customData1)))
						.andReturn();
				
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void createCustomData_Failed() throws Exception {
		String url = baseUrl + "/custom-data";
		
		Mockito
		.when(contactService.contactExists(Mockito.any()))
		.thenReturn(true);
		
		Mockito
		.when(customFieldService.customFieldExists(Mockito.any()))
		.thenReturn(true);
		
		Mockito
		.when(customFieldService.saveCustomData(Mockito.any()))
		.thenReturn(false);
		
		MvcResult result = mockMvc.perform(post(url)
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(customData1)))
						.andReturn();
				
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void createCustomData_Contact_NotFound() throws Exception {
		String url = baseUrl + "/custom-data";
		
		Mockito
		.when(contactService.contactExists(Mockito.any()))
		.thenReturn(false);
		
		MvcResult result = mockMvc.perform(post(url)
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(customData1)))
						.andReturn();
				
		assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void createCustomData_Field_NotFound() throws Exception {
		String url = baseUrl + "/custom-data";
		
		Mockito
		.when(contactService.contactExists(Mockito.any()))
		.thenReturn(true);
		
		Mockito
		.when(customFieldService.customFieldExists(Mockito.any()))
		.thenReturn(false);
		
		MvcResult result = mockMvc.perform(post(url)
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(customData1)))
						.andReturn();
				
		assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void getCustomFields_Success() throws Exception {
		String url = baseUrl + "/custom-fields";
		
		Mockito
		.when(customFieldService.getAllCustomFields())
		.thenReturn(customFieldsList());
		
		MvcResult result = mockMvc.perform(get(url)).andReturn();
				
		List<CustomField> jsonResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<List<CustomField>>() {});
		
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		assertEquals(3, jsonResponse.size());
	}
	
	@Test
	public void getCustomDataByContact_Success() throws Exception {
		Long id = 1L;
		String url = baseUrl + "/custom-data/" + id;
		
		Mockito
		.when(contactService.contactExists(Mockito.any()))
		.thenReturn(true);
		
		Mockito
		.when(customFieldService.getCustomDataByContactId(Mockito.any()))
		.thenReturn(customDataList());
		
		MvcResult result = mockMvc.perform(get(url)).andReturn();
				
		Map jsonResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Map.class);
				
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		assertEquals(3, jsonResponse.size());
	}
	
	@Test
	public void getCustomDataByContact_Contact_NotFound() throws Exception {
		Long id = 1L;
		String url = baseUrl + "/custom-data/" + id;
		
		Mockito
		.when(contactService.contactExists(Mockito.any()))
		.thenReturn(false);
		
		MvcResult result = mockMvc.perform(get(url)).andReturn();
			
		assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
	}
	
	public List<CustomField> customFieldsList() {
		List<CustomField> results = new ArrayList<>();
		results.add(customField1);
		results.add(customField2);
		results.add(customField3);
		
		return results;
	}
	
	public Map customDataList() {
		Map results = new HashMap<>();
		results.put("employer", "Octo Consulting Group");
		results.put("yes-no", "Yes");
		results.put("age", 25);
		
		return results;
	}
	
}
