package com.example.guestbook.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.guestbook.controller.guestentry.GuestEntryController;
import com.example.guestbook.model.GuestbookEntry;
import com.example.guestbook.model.Role;
import com.example.guestbook.model.User;
import com.example.guestbook.service.AdminService;
import com.example.guestbook.service.GuestbookEntryService;
import com.example.guestbook.utility.FileStorageService;
import com.example.guestbook.utility.JwtTokenFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = GuestEntryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class GuestEntryControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	AdminService adminService;
	
	@MockBean 
	private JwtTokenFilter jwtTokenFilter;

	@MockBean
	GuestbookEntryService guestbookEntryService;
	
	
	
	@MockBean
    private FileStorageService fileStorageService;
	
	static GuestbookEntry mockGuestbookEntry;
	
	static User mockUser;
	
	
	@BeforeAll
	public static void setUP() {
		
		Role roleAdmin = new Role("ROLE_ADMIN");
		List<Role> roleListAdmin = new ArrayList<Role>();
		roleListAdmin.add(roleAdmin);
	
		mockUser = new User(1L,"subrata", "pass123", "subrata", "saha", "test@123",roleListAdmin);
		

		mockGuestbookEntry = new GuestbookEntry(1L, "comment1", "photos1", false, mockUser);

		
	}

	@Test
	@WithMockUser(roles={"GUEST"})
	public void saveGuestEntry() throws Exception {
		
		Mockito.when(adminService.userByUserId(1L)).thenReturn(mockUser.toDto());
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/guestusers/v1/guestentry/1")
			      .contentType(MediaType.APPLICATION_JSON_VALUE).param("comment", "This is test comment")).andReturn();
		int status = mvcResult.getResponse().getStatus();
		
		assertEquals(200, status);
		
	}
	
	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

}
