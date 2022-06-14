package com.example.guestbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.guestbook.controller.dto.RoleDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role")
@Setter
@Getter
@NoArgsConstructor

public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	public Role(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	public RoleDto toDto(){
	     return RoleDto.builder()
	    		 .name(this.name)
	             .build();
	 }
}
