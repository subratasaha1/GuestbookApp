package com.example.guestbook.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.guestbook.controller.dto.RoleDto;
import com.example.guestbook.controller.dto.UserDto;
import com.example.guestbook.model.Role;
import com.example.guestbook.model.User;
import com.example.guestbook.repository.UserRepository;
import com.example.guestbook.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private UserRepository userRepository;
	
	public AdminServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	public List<UserDto> loadAllUsers(){
		List<User> userList = userRepository.findAll();
		List<UserDto> userDtoList = null;
		if(userList != null && !userList.isEmpty()) {
			userDtoList = new ArrayList<UserDto>();
			for(User user : userList) {
				UserDto userDto = user.toDto();
				userDtoList.add(userDto);
				if(user.getRoles() != null && !user.getRoles().isEmpty()) {
					for(Role role : user.getRoles()) {
						
						RoleDto roleDto = role.toDto();
						userDto.addRole(roleDto);
					}
				}
			}
		}
		
		return userDtoList;
	}
	
	public UserDto userByUserId(Long userId) {
		Optional<User> userOption = userRepository.findById(userId);
		UserDto userDto = null;
		if(userOption.isPresent()) {
			User user = userOption.get();
			userDto = user.toDto();
			if(user.getRoles() != null && !user.getRoles().isEmpty()) {
				for(Role role : user.getRoles()) {
					
					RoleDto roleDto = role.toDto();
					userDto.addRole(roleDto);
				}
			}
			
		}
		return userDto;
	}
	
	
}
