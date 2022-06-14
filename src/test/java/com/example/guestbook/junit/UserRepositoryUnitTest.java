package com.example.guestbook.junit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.guestbook.model.Role;
import com.example.guestbook.model.User;
import com.example.guestbook.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class UserRepositoryUnitTest {

	@Autowired
	private UserRepository userRepository;
	
	static User mockuser;
	static User mockuser2;
	
	List<User> userList = new ArrayList<User>();
	
	@BeforeAll
	public static void setUp() {
		Role roleGuest = new Role("ROLE_GUEST");
		List<Role> roleListGuest = new ArrayList<Role>();
		roleListGuest.add(roleGuest);
		
		mockuser = new User();
		mockuser2 = new User();
		
		mockuser.setUserName("sam");
		mockuser.setFirstName("Mary");
		mockuser.setLastName("King");
		mockuser.setEmail("test1@123");
		mockuser.setPassword("pass123");
		mockuser.setRoles(roleListGuest);
		
		
		Role roleAdmin = new Role("ROLE_ADMIN");
		List<Role> roleListAdmin = new ArrayList<Role>();
		roleListGuest.add(roleAdmin);
		
		mockuser2.setUserName("sam2");
		mockuser2.setFirstName("Mary");
		mockuser2.setLastName("King");
		mockuser2.setEmail("test@123");
		mockuser2.setPassword("pass123");
		mockuser2.setRoles(roleListAdmin);
				
	}
	
	
	@Test
    void findSpecificUserById() {
		
		System.out.println(mockuser.toString());
		
		userRepository.save(mockuser2);
		User saveduser = userRepository.findById(1L).get();
        assertNotNull(saveduser);
    }
	
	@Test
    void findAllUsers() {
		userRepository.save(mockuser2);
        List<User> userList = userRepository.findAll();
        assertTrue(userList.size()>0);
    }
	
}
