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

import com.contact.db.model.Address;
import com.contact.db.model.Contact;
import com.contact.db.model.SearchCriteria;
import com.contact.db.model.dto.ContactDto;
import com.contact.db.repository.DatabaseOperations;
import com.contact.db.service.ContactService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(ContactController.class)
public class ContactControllerTests {

	private final String baseUrl = "http://localhost:8080/v1/api";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ContactService contactService;
	
	@MockBean
	private DatabaseOperations databaseOperations;
	
	private SearchCriteria searchCriteria;
	private Address testAddress;
	private Contact testContact1;
	private Contact testContact2;
	private Contact testContact3;
	private Map customData1 = new HashMap<>();
	private Map customData2 = new HashMap<>();
	private Map customData3 = new HashMap<>();
	
	@Before
	public void init() {
		//Create test Search Criteria
		searchCriteria = new SearchCriteria(null, "Doe", null, null, new Address(null, null, null, 22033));
		
		//Create a test Address
		testAddress = new Address("123 Main St.", "Fairfax", "VA", 22033);
		
		//Create three test Contacts
		testContact1 = new Contact("John", "Doe", "email@address.com", "7031231234", testAddress);
		testContact2 = new Contact("Jane", "Doe", "email@address.com", "7031231234", testAddress);
		testContact3 = new Contact("John", "Smith", "email@address.com", "7031231234", testAddress);
	
		//Create test Custom Data for contact1
		customData1.put("age", 25);
		customData1.put("employer", "Octo Consulting Group");
		
		//Create test Custom Data for contact2
		customData2.put("age", 30);
		customData2.put("employer", "Remax Premier");
		customData2.put("title", "Realtor");
		
		//Create test Custom Data for contact3
		customData3.clear();
		
	}
	
	@Test
	public void createContact_Success() throws Exception {
		String url = baseUrl + "/contact";
		
		Mockito
		.when(contactService.saveContact(Mockito.any()))
		.thenReturn(testContact1);
		
		MvcResult result = mockMvc.perform(post(url)
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(testContact1)))
						.andReturn();
				
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void getContacts_Success() throws Exception {
		String url = baseUrl + "/contacts?sortBy=last_name&direction=asc&customFields=false";
		
		Mockito
		.when(contactService.findAllDtos(Mockito.anyString(), Mockito.anyString()))
		.thenReturn(testContactList());
		
		MvcResult result = mockMvc.perform(get(url)).andReturn();
				
		List<ContactDto> jsonResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<List<ContactDto>>() {});
		
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		assertEquals(3, jsonResponse.size());
	}
	
	@Test
	public void getFilteredContacts_Success() throws Exception {
		String url = baseUrl + "/filtered-contacts?sortBy=last_name&direction=asc&customFields=false";
		
		Mockito
		.when(contactService.findAllDtos(Mockito.anyString(), Mockito.anyString()))
		.thenReturn(testContactList());
		
		Mockito
		.when(contactService.filterResults(Mockito.any(), Mockito.any()))
		.thenReturn(testFilteredContactList());
				
		MvcResult result = mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(searchCriteria)))
				.andReturn();
				
		List<ContactDto> jsonResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<List<ContactDto>>() {});
		
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		assertEquals(2, jsonResponse.size());
	}
	
	@Test
	public void getContacts_WithCustomData_Success() throws Exception {
		String url = baseUrl + "/contacts?sortBy=last_name&direction=asc&customFields=true";
		
		Mockito
		.when(contactService.findAllDtosWithCustomData(Mockito.anyString(), Mockito.anyString()))
		.thenReturn(testContactListWithCustomData());
		
		MvcResult result = mockMvc.perform(get(url)).andReturn();
						
		List<ContactDto> jsonResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<List<ContactDto>>() {});

		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		assertEquals(3, jsonResponse.size());
		assertEquals(2, jsonResponse.get(0).getCustomData().size());
		assertEquals(3, jsonResponse.get(1).getCustomData().size());
		assertEquals(0, jsonResponse.get(2).getCustomData().size());
	}
	
	@Test
	public void getFilteredContacts_WithCustomData_Success() throws Exception {
		String url = baseUrl + "/filtered-contacts?sortBy=last_name&direction=asc&customFields=true";
		
		Mockito
		.when(contactService.findAllDtos(Mockito.anyString(), Mockito.anyString()))
		.thenReturn(testContactListWithCustomData());
		
		Mockito
		.when(contactService.filterResults(Mockito.any(), Mockito.any()))
		.thenReturn(testFilteredContactListWithCustomData());
		
		MvcResult result = mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(searchCriteria)))
				.andReturn();
				
		List<ContactDto> jsonResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<List<ContactDto>>() {});
		
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		assertEquals(2, jsonResponse.size());
		assertEquals(2, jsonResponse.get(0).getCustomData().size());
		assertEquals(3, jsonResponse.get(1).getCustomData().size());
	}
	
	@Test
	public void getContacts_BadRequest() throws Exception {
		String url = baseUrl + "/contacts?sortBy=last_name&direction=bad&customFields=false";
		
		MvcResult result = mockMvc.perform(get(url)).andReturn();
				
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void getFilteredContacts_BadRequest() throws Exception {
		String url = baseUrl + "/filtered-contacts?sortBy=last_name&direction=bad&customFields=false";
		
		MvcResult result = mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(searchCriteria)))
				.andReturn();
				
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
	}
	
	public List<ContactDto> testContactList() {
		List<ContactDto> results = new ArrayList<>();
		results.add(new ContactDto(testContact1));
		results.add(new ContactDto(testContact2));
		results.add(new ContactDto(testContact3));
		
		return results;
	}
	
	public List<ContactDto> testFilteredContactList() {
		List<ContactDto> results = new ArrayList<>();
		results.add(new ContactDto(testContact1));
		results.add(new ContactDto(testContact2));
		
		return results;
	}
	
	public List<ContactDto> testContactListWithCustomData() {
		List<ContactDto> results = new ArrayList<>();
		
		results.add(new ContactDto(testContact1, customData1));
		results.add(new ContactDto(testContact2, customData2));
		results.add(new ContactDto(testContact3, customData3));
		
		return results;
	}
	
	public List<ContactDto> testFilteredContactListWithCustomData() {
		List<ContactDto> results = new ArrayList<>();
		
		results.add(new ContactDto(testContact1, customData1));
		results.add(new ContactDto(testContact2, customData2));
		
		return results;
	}
	
}
