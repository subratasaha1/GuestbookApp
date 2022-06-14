package com.example.guestbook.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor

public class AuthResponse {
	private String username;
	private String accessToken;
}
