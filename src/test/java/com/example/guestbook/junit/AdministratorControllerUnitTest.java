package com.example.guestbook.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.guestbook.controller.admin.AdminstratorController;
import com.example.guestbook.controller.dto.GuestbookEntryDto;
import com.example.guestbook.controller.dto.UserDto;
import com.example.guestbook.model.GuestbookEntry;
import com.example.guestbook.model.Role;
import com.example.guestbook.model.User;
import com.example.guestbook.service.AdminService;
import com.example.guestbook.service.GuestbookEntryService;
import com.example.guestbook.utility.JwtTokenFilter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import constant.MessageConstant;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AdminstratorController.class)
@AutoConfigureMockMvc(addFilters = false)

//@SpringBootTest(classes = TestSecurityConfig.class)
public class AdministratorControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	AdminService adminService;
	
	@MockBean 
	private JwtTokenFilter jwtTokenFilter;

	@MockBean
	GuestbookEntryService guestbookEntryService;


	
	static List<UserDto> mockUserList;
	
	static List<GuestbookEntryDto> mockguestbookEntries;
	
	static User mockUser;
	
	static GuestbookEntry mockGuestbookEntry;
	
	@BeforeAll
	public static void setUP() {
		
		Role roleAdmin = new Role("ROLE_ADMIN");
		List<Role> roleListAdmin = new ArrayList<Role>();
		roleListAdmin.add(roleAdmin);
	
		mockUser = new User(1L,"subrata", "pass123", "subrata", "saha", "test@123",roleListAdmin);
		
		
		
		mockUserList = Arrays.asList(
				new User(1L,"subrata", "pass123", "subrata", "saha", "test@123",roleListAdmin).toDto(),
				new User(1L,"subrata", "pass123", "subrata", "saha", "test@123",roleListAdmin).toDto());
		
		/*
		 * if(mockUserList != null && !mockUserList.isEmpty()) { userDtoList = new
		 * ArrayList<UserDto>(); for(User user : mockUserList) { UserDto userDto =
		 * user.toDto(); userDtoList.add(userDto); if(user.getRoles() != null &&
		 * !user.getRoles().isEmpty()) { for(Role role : user.getRoles()) {
		 * 
		 * } } } }
		 */
		
		mockguestbookEntries = Arrays.asList(
				new GuestbookEntry(1L, "comment1", "photos1", false, mockUser).toDto(),
				new GuestbookEntry(1L, "comment1", "photos1", false, mockUser).toDto());


		mockGuestbookEntry = new GuestbookEntry(1L, "comment1", "photos1", false, mockUser);

		
	}
	
	@Test
	@WithMockUser(roles={"ADMIN"})
	public void getUserByUserId() throws Exception {

		
		Mockito.when(adminService.userByUserId(1L)).thenReturn(mockUser.toDto());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/administrator/users/1")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(200, status);

	}

	@Test
	@WithMockUser(roles={"ADMIN"})
	public void getUserList() throws Exception {

		
		Mockito.when(adminService.loadAllUsers()).thenReturn(mockUserList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/administrator/users")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(200, status);

		//User[] users = this.mapFromJson(result.getResponse().getContentAsString(), User[].class);

		//assertTrue(users.length > 1);

	}

	@Test
	@WithMockUser(roles={"ADMIN"})
	public void getGuestEntries() throws Exception {

		
		Mockito.when(guestbookEntryService.getAllEntriesForUser(1L)).thenReturn(mockguestbookEntries);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/administrator/users/guestentries/1")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(200, status);

	}

	@Test
	@WithMockUser(roles={"ADMIN"})
	public void getGuestEntry() throws Exception {

		Mockito.when(guestbookEntryService.getGuestEntry(1L)).thenReturn(mockGuestbookEntry.toDto());

		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/administrator/guestentries/1")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(200, status);

	}
	
	@Test
	@WithMockUser(roles={"ADMIN"})
	public void updateGuestEntry() throws Exception {
		
		String inputJson = this.mapToJson(mockGuestbookEntry);
		
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/administrator/guestentries/1")
			      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, MessageConstant.GUEST_ENTRY_RECORD_UPDATED);		
	}
	
	@Test
	@WithMockUser(roles={"ADMIN"})
	public void approveGuestEntry() throws Exception {
		
		GuestbookEntryDto mockGuestEntryDto = new GuestbookEntryDto();
		mockGuestEntryDto.setApproved(true);
		
		
		Mockito.when(guestbookEntryService.getGuestEntry(1L)).thenReturn(mockGuestbookEntry.toDto());
		
		String inputJson = this.mapToJson(mockGuestEntryDto);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/api/administrator/guestentries/1",1L)
			      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, MessageConstant.APPROVED_FLAG_UPDATED);		
	}
	
	@Test
	@WithMockUser(roles={"ADMIN"})
	public void removeGuestEntry() throws Exception {
		
		
		
		Mockito.when(guestbookEntryService.getGuestEntry(1L)).thenReturn(mockGuestbookEntry.toDto());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/administrator/guestentries/1",1L)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		int status = result.getResponse().getStatus();
		assertEquals(200, status);
		String content = result.getResponse().getContentAsString();
		assertEquals(content, MessageConstant.GUEST_ENTRY_RECORD_DELETED);		
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

}
