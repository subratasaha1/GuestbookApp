package com.example.guestbook.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestbookEntryDto {
	
	private boolean isApproved;
	
	private long id;
	
	private String comment;
	
	private String photos;
	
	private long userid;;
	
	
	
	/*
	 * public void setIsApproved(Boolean isApproved) { this.isApproved = isApproved;
	 * }
	 * 
	 * public Boolean getIsApproved() { return this.isApproved; }
	 */
}
