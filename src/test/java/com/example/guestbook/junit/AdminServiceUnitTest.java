package com.example.guestbook.junit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.guestbook.model.Role;
import com.example.guestbook.model.User;
import com.example.guestbook.repository.UserRepository;
import com.example.guestbook.service.impl.AdminServiceImpl;

@RunWith(SpringRunner.class)
public class AdminServiceUnitTest {

	private static UserRepository userRepository;
	
	private static AdminServiceImpl adminService;
	
	
	
	static User mockUser;
	static List<User> mockUserList;
	
	
	@BeforeAll
	public static void setUP() {
		
		  
		userRepository = mock(UserRepository.class);
		
		adminService = new AdminServiceImpl(userRepository);
		
		Role roleGuest = new Role("ROLE_GUEST");
		List<Role> roleListGuest = new ArrayList<Role>();
		roleListGuest.add(roleGuest);
		
		Role roleAdmin = new Role("ROLE_ADMIN");
		List<Role> roleListAdmin = new ArrayList<Role>();
		roleListAdmin.add(roleAdmin);
		
		mockUser = new User(1L,"sam", "pass123", "Mary", "King", "test@123",roleListGuest);
		  
		mockUserList = Arrays.asList( 
				new User(1L,"sam", "pass123", "Mary", "King", "test@123",roleListGuest), 
				new User(1L,"shan", "pass123", "Shan", "Alex","test@123",roleListAdmin));
	}
	
	@Test
	public void loadAllUsers(){
		
		
		when(userRepository.findAll()).thenReturn(mockUserList);
		
				
		assertTrue(adminService.loadAllUsers().size()>1);
		
	}
	@Test
	public void userByUserId() {
		
		when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
		
		assertNotNull(adminService.userByUserId(1L));
	}
	
}
