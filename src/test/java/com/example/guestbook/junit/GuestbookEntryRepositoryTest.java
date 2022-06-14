package com.example.guestbook.junit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.guestbook.model.GuestbookEntry;
import com.example.guestbook.model.User;
import com.example.guestbook.repository.GuestbookEntryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GuestbookEntryRepositoryTest {
		
	@Autowired
	private GuestbookEntryRepository guestbookEntryRepository;
	
	@Test
    void saveGuestbookEntry() {
		User user = new User();
		user.setId(1L);
		GuestbookEntry guestbookEntry1 = new GuestbookEntry("Test Comment 1","photo_url_1",false,user);
		guestbookEntryRepository.save(guestbookEntry1);
        assertTrue(guestbookEntry1.getId()>0);
    }
	
	
	@Test
    void findSpecificGuestbookEntryById() {
		User user = new User();
		user.setId(1L);
		GuestbookEntry guestbookEntry1 = new GuestbookEntry("Test Comment 1","photo_url_1",false,user);
		guestbookEntryRepository.save(guestbookEntry1);
        GuestbookEntry guestbookEntry = guestbookEntryRepository.findById(guestbookEntry1.getId()).get();
        assertEquals(guestbookEntry1, guestbookEntry);
    }
	
	@Test
    void findAllGuestbookEntriesByUserId() {
		User user = new User();
		user.setId(1L);
		GuestbookEntry guestbookEntry1 = new GuestbookEntry("Test Comment 1","photo_url_1",false,user);
		GuestbookEntry guestbookEntry2 = new GuestbookEntry("Test Comment 2","photo_url_2",false,user);
		guestbookEntryRepository.save(guestbookEntry1);	
		guestbookEntryRepository.save(guestbookEntry2);
		List<GuestbookEntry> collections = List.of(guestbookEntry1, guestbookEntry1);
        List<GuestbookEntry> guestbookEntryList = guestbookEntryRepository.findAllEntriesByUserId(1L);
        assertTrue(guestbookEntryList.containsAll(collections));
    }
	
	@Test
    void deleteGuestbookEntriesById() {
		User user = new User();
		user.setId(1L);
		GuestbookEntry guestbookEntry1 = new GuestbookEntry("Test Comment 1","photo_url_1",false,user);
		GuestbookEntry guestbookEntry2 = new GuestbookEntry("Test Comment 2","photo_url_2",false,user);
		guestbookEntryRepository.save(guestbookEntry1);
		guestbookEntryRepository.save(guestbookEntry2);
		guestbookEntryRepository.delete(guestbookEntry2);
        List<GuestbookEntry> guestbookEntryList = guestbookEntryRepository.findAllEntriesByUserId(1L);
        assertFalse(guestbookEntryList.contains(guestbookEntry2));
    }
	
	@Test
    void updateGuestbookEntriesById() {
		User user = new User();
		user.setId(1L);
		GuestbookEntry guestbookEntry1 = new GuestbookEntry("Test Comment 1","photo_url_1",false,user);
		guestbookEntryRepository.save(guestbookEntry1);
		guestbookEntry1.setComment("Modified Test Comment");
		guestbookEntryRepository.save(guestbookEntry1);
        GuestbookEntry modguestbookEntry = guestbookEntryRepository.findById(guestbookEntry1.getId()).get();
        assertTrue("Modified Test Comment".equals(modguestbookEntry.getComment()));
    }
}
