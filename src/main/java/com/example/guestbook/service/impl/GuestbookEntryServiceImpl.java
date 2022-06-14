package com.example.guestbook.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.guestbook.controller.dto.GuestbookEntryDto;
import com.example.guestbook.model.GuestbookEntry;
import com.example.guestbook.model.User;
import com.example.guestbook.repository.GuestbookEntryRepository;
import com.example.guestbook.service.GuestbookEntryService;

import constant.MessageConstant;

@Service
public class GuestbookEntryServiceImpl implements GuestbookEntryService{
	
	@Autowired
	private GuestbookEntryRepository guestbookEntryRepository;
	
	public GuestbookEntryServiceImpl(GuestbookEntryRepository guestbookEntryRepository) {
		this.guestbookEntryRepository = guestbookEntryRepository;
	}

	@Override
	public List<GuestbookEntryDto> getAllEntriesForUser(Long userId){
		List<GuestbookEntry> guestbookEntryList = guestbookEntryRepository.findAllEntriesByUserId(userId);
		
		List<GuestbookEntryDto> guestbookEntryDtoList = null;
		if(guestbookEntryList != null && !guestbookEntryList.isEmpty()) {
			guestbookEntryDtoList = new ArrayList<GuestbookEntryDto>();
			for(GuestbookEntry guestbookEntry : guestbookEntryList) {
				GuestbookEntryDto guestbookEntryDto = guestbookEntry.toDto();
				guestbookEntryDtoList.add(guestbookEntryDto);
			}
		}
		return guestbookEntryDtoList;
	}
	
	@Override
	public GuestbookEntryDto getGuestEntry(Long guestEntryId){
		Optional<GuestbookEntry> guestbookEntryOption = guestbookEntryRepository.findById(guestEntryId);
		if(guestbookEntryOption.isPresent()) {
			
			return guestbookEntryOption.get().toDto();
		}else {
			return null;
		}
		
	}

	
	@Override
	public String deleteEntry(GuestbookEntryDto guestbookEntryDto) {
		
		Optional<GuestbookEntry> guestbookEntryOption = guestbookEntryRepository.findById(guestbookEntryDto.getId());
		
		if(guestbookEntryOption.isPresent()) {
						
			guestbookEntryRepository.delete(guestbookEntryOption.get());
			
			return MessageConstant.GUEST_ENTRY_RECORD_DELETED;
		
		}else {
			return MessageConstant.GUEST_ENTRY_RECORD_NOT_PRESENT;
		}
				
	}
		
	@Override
	public String approvedGuestbookEntry(GuestbookEntryDto guestbookEntryDto) {
		
		Optional<GuestbookEntry> guestbookEntryOption = guestbookEntryRepository.findById(guestbookEntryDto.getId());
		
		if(guestbookEntryOption.isPresent()) {
			
			GuestbookEntry guestbookEntry = guestbookEntryOption.get();
			
			guestbookEntry.setApproved(guestbookEntryDto.isApproved());
			
			guestbookEntryRepository.save(guestbookEntry);
			
			return MessageConstant.APPROVED_FLAG_UPDATED;
		}else {
			return MessageConstant.GUEST_ENTRY_RECORD_NOT_PRESENT;
		}
	}
	
	@Override
	public String updateGuestbookEntry(GuestbookEntryDto guestbookEntryDto) {
		
		Optional<GuestbookEntry> guestbookEntryOption = guestbookEntryRepository.findById(guestbookEntryDto.getId());
		
		if(guestbookEntryOption.isPresent()) {
			
			GuestbookEntry guestbookEntry = guestbookEntryOption.get();
			
			guestbookEntry.setApproved(guestbookEntryDto.isApproved());
			guestbookEntry.setComment(guestbookEntryDto.getComment());
			guestbookEntry.setPhotos(guestbookEntryDto.getPhotos());
			
			guestbookEntryRepository.save(guestbookEntry);
			
			return MessageConstant.GUEST_ENTRY_RECORD_UPDATED;
		}else {
			return MessageConstant.GUEST_ENTRY_RECORD_NOT_PRESENT;
		}
	}
	
	@Override
	public GuestbookEntryDto saveGuestbookEntry(GuestbookEntryDto guestbookEntryDto) {
		GuestbookEntry gueesGuestbookEntry = new GuestbookEntry();
		gueesGuestbookEntry.setId(guestbookEntryDto.getId());
		gueesGuestbookEntry.setApproved(guestbookEntryDto.isApproved());
		gueesGuestbookEntry.setComment(guestbookEntryDto.getComment());
		gueesGuestbookEntry.setPhotos(guestbookEntryDto.getPhotos());
		User user = new User();
		user.setId(guestbookEntryDto.getUserid());
		gueesGuestbookEntry.setUser(user);
		GuestbookEntry guestbookEntry1 = guestbookEntryRepository.save(gueesGuestbookEntry);
		
		return guestbookEntry1.toDto();
	}
}
