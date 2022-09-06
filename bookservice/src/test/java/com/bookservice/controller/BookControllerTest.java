package com.bookservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
import com.bookservice.repository.AuthorRepository;
import com.bookservice.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
	@InjectMocks BookController bookController;
	@Mock BookRepository bookRepository;
	@Mock AuthorRepository authorRepository;
	
	public static Book SampleBook() {
		Book book = new Book();
		book.setId(Long.valueOf(1));
		book.setAuthorid(Long.valueOf(1));
		book.setTitle("title");
		book.setCategory("category");
		book.setPrice(500);
		book.setAuthor("author");
		book.setPublisher("publisher");
		book.setPublisheddate("27/03/2022");
		book.setChapters(5);
		book.setActive(true);
		
		return book;
	}
	public static Author SampleAuthor() {
		Author author = new Author(Long.valueOf(1),"abc","abc@gmail.com","abcd",true);
		return author;
	}
	@Test
	void testGetAllBooks() {
		Book book = SampleBook();
		List<Book> books = new ArrayList<Book>();
		books.add(book);
		books.add(book);
		when(bookRepository.findAll()).thenReturn(books) ;
		assertEquals(bookController.getAllBooks().size(),books.size());
		
		
	}

	@Test
	void testSearchBook() {
		Book book = SampleBook();
		List<Book> books = new ArrayList<Book>();
		//
		when(bookRepository.findAll()).thenReturn(books);
		assertEquals(bookController.searchBook( Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty()),new ResponseEntity<String>("NO Books Found",HttpStatus.NOT_FOUND));
		books.add(book);
		when(bookRepository.findAll()).thenReturn(books);
		assertEquals(bookController.searchBook( Optional.ofNullable("author"),Optional.ofNullable("category"),Optional.of(500),Optional.ofNullable("publisher")),
				new ResponseEntity<List<Book>>(books, HttpStatus.FOUND));
	}

	@Test
	void testCreateBook() {
		Book book= SampleBook();
		Author author = SampleAuthor();
		Long authorid= Long.valueOf(1);
		when(authorRepository.findById(authorid)).thenReturn(Optional.ofNullable(author));
		when(authorRepository.existsById(authorid)).thenReturn(true);
		assertEquals(bookController.createBook(book, authorid),ResponseEntity.ok("Book Created Successfully"));
		author.setLoginstatus(false);
		assertEquals(bookController.createBook(book, authorid),ResponseEntity.badRequest().body("Please Login to Create Book"));
		when(authorRepository.existsById(authorid)).thenReturn(false);
		assertEquals(bookController.createBook(book, authorid),new ResponseEntity<String>("Invalid Author to create book",HttpStatus.UNAUTHORIZED));
		
		
	}

	
	@Test
	void testUpdateBook() {
		Book book = SampleBook();
		Author author= SampleAuthor();
		Long authorid=Long.valueOf(1),bookid=Long.valueOf(1);
		when(authorRepository.findById(authorid)).thenReturn(Optional.ofNullable(author));
		when(authorRepository.existsById(bookid)).thenReturn(true);
		assertEquals(bookController.updateBook(book, authorid, bookid),ResponseEntity.ok("Book Updated Successfully"));
		author.setLoginstatus(false);
		when(authorRepository.findById(authorid)).thenReturn(Optional.ofNullable(author));
		assertEquals(bookController.updateBook(book, authorid, bookid),ResponseEntity.badRequest().body("Please Login to Update Book"));
		when(authorRepository.existsById(bookid)).thenReturn(false);
		assertEquals(bookController.updateBook(book, authorid, bookid),new ResponseEntity<String>("No book found to Update",HttpStatus.UNAUTHORIZED));
	}

}
