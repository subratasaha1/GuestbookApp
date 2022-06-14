package com.example.guestbook.junit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.guestbook.controller.dto.GuestbookEntryDto;
import com.example.guestbook.model.GuestbookEntry;
import com.example.guestbook.model.User;
import com.example.guestbook.repository.GuestbookEntryRepository;
import com.example.guestbook.service.impl.GuestbookEntryServiceImpl;

@RunWith(SpringRunner.class)
public class GuestbookEntryServiceUnitTest {

	private static GuestbookEntryRepository guestbookEntryRepository;

	private static GuestbookEntryServiceImpl guestbookEntryServiceImpl;

	static List<GuestbookEntry> mockguestbookEntries;

	static GuestbookEntry mockGuestbookEntry;

	@BeforeAll
	public static void setUP() {
		
		User user = new User();
		user.setId(1L);

		guestbookEntryRepository = mock(GuestbookEntryRepository.class);

		guestbookEntryServiceImpl = new GuestbookEntryServiceImpl(guestbookEntryRepository);

		mockguestbookEntries = Arrays.asList(new GuestbookEntry(1L, "comment1", "photos1", false, user),
				new GuestbookEntry(1L, "comment1", "photos1", false, user));

		mockGuestbookEntry = new GuestbookEntry(1L, "comment1", "photos1", false, user);

	}

	@Test
	public void getAllEntriesForUser() {

		when(guestbookEntryRepository.findAllEntriesByUserId(1L)).thenReturn(mockguestbookEntries);

		assertTrue(guestbookEntryServiceImpl.getAllEntriesForUser(1L).size() > 1);
	}
    @Test
	public void getGuestEntry() {

		when(guestbookEntryRepository.findById(1L)).thenReturn(Optional.of(mockGuestbookEntry));

		assertNotNull(guestbookEntryServiceImpl.getGuestEntry(1L));
	}


    @Test
	public void saveGuestbookEntry() {

		when(guestbookEntryRepository.save(any(GuestbookEntry.class))).thenReturn(mockGuestbookEntry);

		GuestbookEntryDto savedEntry = guestbookEntryServiceImpl.saveGuestbookEntry(mockGuestbookEntry.toDto());
		assertNotNull(savedEntry.getComment());
	}

}
