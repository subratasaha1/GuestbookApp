package com.example.guestbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.guestbook.model.GuestbookEntry;

@Repository
public interface GuestbookEntryRepository extends JpaRepository<GuestbookEntry, Long> {

	public List<GuestbookEntry> findAllEntriesByUserId(Long userId);
}
