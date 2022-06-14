package com.example.guestbook.controller.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

	private Long id;

	private String userName;
	
	private String firstName;
	
	private String lastName;

	private String email;
	
	private List<RoleDto> roleDtoList;
	
	public void addRole(RoleDto roleDto) {
		if(roleDtoList == null) {
			roleDtoList =  new ArrayList<RoleDto>();
		}
		roleDtoList.add(roleDto);
	}
}
