package com.example.guestbook.controller.admin;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.guestbook.controller.dto.GuestbookEntryDto;
import com.example.guestbook.controller.dto.UserDto;
import com.example.guestbook.exception.RecordNotFoundException;
import com.example.guestbook.service.AdminService;
import com.example.guestbook.service.GuestbookEntryService;

import constant.MessageConstant;

@RestController
@RequestMapping("/api/administrator")
public class AdminstratorController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private GuestbookEntryService guestbookEntryService;

	

	/**
	 * This is an rest api.
	 * This returns all the users in system
	 * @return List of User
	 */
	
	@GetMapping("/users")
    @RolesAllowed(MessageConstant.ROLE_ADMIN)
	public ResponseEntity<List<UserDto>> listUsers() {
		List<UserDto> userList = this.adminService.loadAllUsers();
		
		if(userList == null || userList.isEmpty()) {
			throw new RecordNotFoundException(MessageConstant.USER_RECORD_NOT_PRESENT);
		}

		return ResponseEntity.ok(userList);
	}
	
	/**
	 * This rest api returns specific user for a user id
	 * @param userId
	 * @return
	 */

	@GetMapping("/users/{userId}")
	@RolesAllowed(MessageConstant.ROLE_ADMIN)
	public ResponseEntity<UserDto> userByUserId(@PathVariable("userId") Long userId) {
		
		UserDto user = this.adminService.userByUserId(userId);
				
		if(user == null) {
			throw new RecordNotFoundException(MessageConstant.USER_RECORD_NOT_PRESENT);
		}

		return ResponseEntity.ok(user);
	}

	/***
	 * This rest api returns all the entries for a specific user id
	 * if there is no record for a specific user id then it
	 * returns RecordNotFoundException
	 * @param id 
	 * @return
	 */
	
	@GetMapping("/users/guestentries/{userId}")
	@RolesAllowed(MessageConstant.ROLE_ADMIN)
	public ResponseEntity<List<GuestbookEntryDto>> getEntriesForUser(@PathVariable("userId") Long id){
		
		List<GuestbookEntryDto> guestbookEntryList = guestbookEntryService.getAllEntriesForUser(id);
		
		if(guestbookEntryList != null && !guestbookEntryList.isEmpty()) {
			
			return ResponseEntity.ok(guestbookEntryList);
			
		}else {
			throw new RecordNotFoundException(MessageConstant.GUEST_ENTRY_RECORD_NOT_PRESENT+ id);
		}
		
	}
	
	/***
	 * This rest api returns specific guest entry
	 * if there is no record for a specific user id then it
	 * returns RecordNotFoundException
	 * @param id
	 * @return
	 */
	
	@GetMapping("/guestentries/{guestentryId}")
	@RolesAllowed(MessageConstant.ROLE_ADMIN)
	public ResponseEntity<GuestbookEntryDto> getGuestEntry(@PathVariable("guestentryId") Long id){
		
		GuestbookEntryDto guestbookEntry = guestbookEntryService.getGuestEntry(id);
		
		if(guestbookEntry != null) {
			
			return ResponseEntity.ok(guestbookEntry);
			
		}else {
			throw new RecordNotFoundException(MessageConstant.GUEST_ENTRY_RECORD_NOT_PRESENT+ id);
		}
		
	}
	
	/**
	 * This rest api deletes specific guest entry
	 * @param id
	 * @return
	 */
	
	@DeleteMapping("/guestentries/{guestentryId}")
	@RolesAllowed(MessageConstant.ROLE_ADMIN)
	public ResponseEntity<String> removeGuestEntry(@PathVariable("guestentryId") Long id){
		
		GuestbookEntryDto guestEntryDto = new GuestbookEntryDto();
		guestEntryDto.setId(id);
		
		String message = guestbookEntryService.deleteEntry(guestEntryDto);
		
		if(MessageConstant.GUEST_ENTRY_RECORD_NOT_PRESENT.equals(message)) {
			throw new RecordNotFoundException(MessageConstant.GUEST_ENTRY_RECORD_NOT_PRESENT+ id);
		}else {
			return ResponseEntity.ok(MessageConstant.GUEST_ENTRY_RECORD_DELETED);
			
		}
				
	}
	
	/**
	 * This api approves a specific guest entry
	 * @param guestbookEntry
	 * @return
	 */
	
	@PatchMapping("/guestentries/{guestentryId}")
	@RolesAllowed(MessageConstant.ROLE_ADMIN)
	public ResponseEntity<String> approveGuestEntry(
			@PathVariable("guestentryId") Long id,
			@RequestBody GuestbookEntryDto guestbookEntryDto){
		

		
		guestbookEntryDto.setId(id);
		
		String message = guestbookEntryService.approvedGuestbookEntry(guestbookEntryDto);
		
		if(MessageConstant.GUEST_ENTRY_RECORD_NOT_PRESENT.equals(message)) {
			throw new RecordNotFoundException(MessageConstant.GUEST_ENTRY_RECORD_NOT_PRESENT+ id);
		}else {
			return ResponseEntity.ok(MessageConstant.APPROVED_FLAG_UPDATED);
			
			
		}
		
	}
	
	/**
	 * This api updates a specific guest entry
	 * @param guestbookEntry
	 * @return
	 */
	
	@PutMapping("/guestentries/{guestentryId}")
	@RolesAllowed(MessageConstant.ROLE_ADMIN)
	public ResponseEntity<String> updateGuestEntry(@RequestBody GuestbookEntryDto guestbookEntryDto){
		
				
		String message = guestbookEntryService.updateGuestbookEntry(guestbookEntryDto);
		
		if(MessageConstant.GUEST_ENTRY_RECORD_NOT_PRESENT.equals(message)) {
			throw new RecordNotFoundException(MessageConstant.GUEST_ENTRY_RECORD_NOT_PRESENT+ guestbookEntryDto.getId());
		}else {
			return ResponseEntity.ok(MessageConstant.GUEST_ENTRY_RECORD_UPDATED);
			
			
		}
		
	}
}
