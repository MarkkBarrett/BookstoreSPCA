package com.example.aaaBookstoreCA.repository;

import com.example.aaaBookstoreCA.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Book Repo with Spring for Crud
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	// search and filter methods
    // find books that match a given title
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Same for author
    List<Book> findByAuthorContainingIgnoreCase(String author);

    // Same for publisher
    List<Book> findByPublisherContainingIgnoreCase(String publisher);

    // filtering by category
    List<Book> findByCategoryIgnoreCase(String category);
}
