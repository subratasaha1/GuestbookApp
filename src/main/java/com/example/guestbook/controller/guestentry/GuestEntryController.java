package com.example.guestbook.controller.guestentry;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.guestbook.controller.dto.GuestbookEntryDto;
import com.example.guestbook.controller.dto.UserDto;
import com.example.guestbook.exception.InvalidInputException;
import com.example.guestbook.exception.RecordNotFoundException;
import com.example.guestbook.service.AdminService;
import com.example.guestbook.service.GuestbookEntryService;
import com.example.guestbook.utility.FileStorageService;

import constant.MessageConstant;

@RestController
@RequestMapping("api/guestusers/v1/")
public class GuestEntryController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private GuestbookEntryService guestbookEntryService;
	
	@Autowired
    private FileStorageService fileStorageService;
	
	


	@PostMapping("/guestentry/{userId}")
	@RolesAllowed(MessageConstant.ROLE_GUEST)
	ResponseEntity<String> createGuestEntry(@PathVariable("userId") Long id,
			@RequestParam(required = false) MultipartFile file,
			@RequestParam(required=false) String comment)
			throws Exception {
		
		System.out.println("createGuestEntry");
		
		if(file == null && comment == null) {
			throw new InvalidInputException("Provide either file or comment");
		}
		
		UserDto user = this.adminService.userByUserId(id);
		
		
		
		if(user != null) {
			//String fileDownloadUri = null;
			String fileName = null;
			if(file != null) {
				fileName = fileStorageService.storeFile(file,id.toString());

				/*
				 * fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				 * .path("/downloadFile/") .path(fileName) .toUriString();
				 */
			}
			
			
	        
	        
	        GuestbookEntryDto guestbookEntry = new GuestbookEntryDto();
	        guestbookEntry.setUserid(user.getId());
	        guestbookEntry.setComment(comment);
	        guestbookEntry.setPhotos(fileName);
	        guestbookEntry.setApproved(false);
	        
	        guestbookEntryService.saveGuestbookEntry(guestbookEntry);

	        return ResponseEntity.ok(MessageConstant.GUEST_ENTRY_RECORD_CREATED);
	        

		}else {
			throw new RecordNotFoundException("User record is not present for user id "+ id);
		}

		
	}

}
