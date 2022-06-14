package com.example.guestbook.service;

import java.util.List;

import com.example.guestbook.controller.dto.UserDto;

public interface AdminService {
	public List<UserDto> loadAllUsers();
	
	public UserDto userByUserId(Long userId);
	
}
