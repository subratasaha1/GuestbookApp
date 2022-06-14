package com.example.guestbook.service;

import java.util.List;

import com.example.guestbook.controller.dto.GuestbookEntryDto;

public interface GuestbookEntryService {

	public List<GuestbookEntryDto> getAllEntriesForUser(Long userId);
	
	public GuestbookEntryDto getGuestEntry(Long guestEntryId);
	
	public String deleteEntry(GuestbookEntryDto guestbookEntry);
	
	public String approvedGuestbookEntry(GuestbookEntryDto guestbookEntry);
	
	public String updateGuestbookEntry(GuestbookEntryDto guestbookEntry);
	
	public GuestbookEntryDto saveGuestbookEntry(GuestbookEntryDto guestbookEntry);
}
