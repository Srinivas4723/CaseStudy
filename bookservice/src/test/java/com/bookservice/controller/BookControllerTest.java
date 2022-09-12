package com.bookservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.bookservice.entity.Author;
import com.bookservice.entity.Book;
import com.bookservice.entity.Category;
import com.bookservice.repository.AuthorRepository;
import com.bookservice.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
	
	@InjectMocks BookController bookController;
	@Mock BookRepository bookRepository;
	@Mock AuthorRepository authorRepository;
	
	private  Book SampleBook() {
		Book book = new Book();
		book.setId(Long.valueOf(1));
		book.setAuthorid(Long.valueOf(1));
		book.setTitle("title");
		book.setCategory(Category.COMICS);
		book.setPrice(BigDecimal.valueOf(500.00));
		book.setAuthor("author");
		book.setPublisher("publisher");
		book.setPublisheddate(LocalDate.of(1997,Month.SEPTEMBER,5));
		book.setChapters(5);
		book.setActive(true);
		return book;
	}
	private  Author SampleAuthor() {
		Author author = new Author();
		author.setId(Long.valueOf(1));
		author.setAuthorname("abc");
		author.setAuthoremail("abc@gmail.com");
		author.setPassword("abcd");
		author.setLoginstatus(false);
		return author;
	}
	Author author= SampleAuthor();
	Book book= SampleBook();
	/**
	 * Find All Books Test Case
	 */
	@Test
	void testGetAllBooks() {
		Book book = SampleBook();
		List<Book> books = new ArrayList<Book>();
		books.add(book);
		books.add(book);
		when(bookRepository.findAll()).thenReturn(books) ;
		assertEquals(bookController.getAllBooks().size(),books.size());
	}
	/**
	 * Find All Books By Author Id
	 */
	@Test
	void testGetBooksByAuthorId() {
		List<Book> books = new ArrayList<Book>();
		books.add(book);
		books.add(book);
		when(bookRepository.findAllByAuthorid(author.getId())).thenReturn(books) ;
		assertEquals(bookController.getBooksByAuthorId(author.getId()).size(),books.size());
	}
	
	/**
	 * BookSearch By Empty Fields
	 */
	@Test
	void testSearchBookByEmptyFields() {
		List<Book> books = new ArrayList<Book>();
		when(bookRepository.findAll()).thenReturn(books);
		assertEquals(bookController.searchBook( Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty()),
				new ResponseEntity<String>("NO Books Found",HttpStatus.NOT_FOUND));
	}
	/**
	 * Search Book By Non-Empty Fields
	 */
	@Test
	void testSearchBookNonEmptyFields() {
		List<Book> books = new ArrayList<Book>();
		books.add(book);
		when(bookRepository.findAll()).thenReturn(books);
		assertEquals(bookController.searchBook( Optional.ofNullable("author"),Optional.ofNullable(Category.COMICS),Optional.of(BigDecimal.valueOf(500.00)),Optional.ofNullable("publisher")),
				new ResponseEntity<List<Book>>(books, HttpStatus.FOUND));
	}
	/**
	 * Create Book success Case
	 */
	@Test
	void testCreateBookSuccess() {
		when(authorRepository.findById(author.getId())).thenReturn(Optional.ofNullable(author));
		author.setLoginstatus(true);
		assertEquals(bookController.createBook(book, author.getId()),ResponseEntity.ok("Book Created Successfully"));
	}
	/**
	 * Create Book Fail By Not LoggedIn
	 */
	@Test
	void testCreateBookFailByNotLoggedIn() {
		author.setLoginstatus(false);
		when(authorRepository.findById(author.getId())).thenReturn(Optional.ofNullable(author));
		assertEquals(bookController.createBook(book, author.getId()),ResponseEntity.badRequest().body("Please Login to Create Book"));
	}
	/**
	 * Create Book Fail By Invalid Author
	 */
	@Test
	void testCreateBookFailByInvalidAuthor() {
		when(authorRepository.findById(author.getId())).thenReturn(Optional.empty());
		assertEquals(bookController.createBook(book, author.getId()),new ResponseEntity<>("Invalid Author to create book",HttpStatus.UNAUTHORIZED));
	}
	/**
	 * Update Book Success
	 */
	@Test
	void testUpdateBookSuccess() {
		author.setLoginstatus(true);
		when(authorRepository.findById(author.getId())).thenReturn(Optional.ofNullable(author));
		when(bookRepository.existsById(book.getId())).thenReturn(true);
		assertEquals(bookController.updateBook(book, author.getId(), book.getId()),ResponseEntity.ok("Book Updated Successfully"));
	}
	/**
	 * Update Book Fail By Not LoggedIn
	 */
	@Test
	void testUpdateBookFailByNotLoggedIn() {
		author.setLoginstatus(false);
		when(authorRepository.findById(author.getId())).thenReturn(Optional.ofNullable(author));
		when(bookRepository.existsById(book.getId())).thenReturn(true);
		assertEquals(bookController.updateBook(book, author.getId(), book.getId()),ResponseEntity.badRequest().body("Please Login to Update Book"));
	}
	/**
	 * Update Book Fail By Invalid Author
	 */
	@Test
	void testUpdateBookFailByInvalidAuthor() {
		author.setLoginstatus(false);
		when(authorRepository.findById(author.getId())).thenReturn(Optional.empty());
		when(bookRepository.existsById(book.getId())).thenReturn(true);
		assertEquals(bookController.updateBook(book, author.getId(), book.getId()),ResponseEntity.badRequest().body("Invalid Author"));
	}
	/**
	 * Update Book Fail By Invalid Book
	 */
	@Test
	void testUpdateBookFailByInvalidBook() {
		when(bookRepository.existsById(book.getId())).thenReturn(false);
		assertEquals(bookController.updateBook(book, author.getId(), book.getId()),new ResponseEntity<String>("No book found to Update",HttpStatus.UNAUTHORIZED));
	}
}
