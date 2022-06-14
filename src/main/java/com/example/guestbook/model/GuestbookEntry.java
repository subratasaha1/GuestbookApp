package com.example.guestbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.guestbook.controller.dto.GuestbookEntryDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="guestbook_entry")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestbookEntry {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="guestbook_comment")
	private String comment;
	
	@Column(name="photos")
	private String photos;
	
	@Column(name="approved_indicator")
	private boolean isApproved;
	@ManyToOne()
	private User user;
	public GuestbookEntry(String comment, String photos, boolean isApproved, User user) {
		super();
		this.comment = comment;
		this.photos = photos;
		this.isApproved = isApproved;
		this.user = user;
	}
	
	public GuestbookEntryDto toDto(){
	     return GuestbookEntryDto.builder()
	             .comment(this.comment)
	             .id(this.id)
	             .isApproved(this.isApproved)
	             .photos(this.photos)
	             .userid(user.getId())
	             .build();
	 } 
	
	
	
}
