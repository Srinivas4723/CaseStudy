package com.bookservice.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bookservice.entity.Author;
import com.bookservice.entity.Book;
import com.bookservice.repository.AuthorRepository;
import com.bookservice.repository.BookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController // spring bean
@RequestMapping("/api/v1/digitalbooks")
public class BookController extends BaseController {// accept requests
	@Autowired // DI
	BookRepository bookRepository; // dependency
	
	@Autowired // DI
	AuthorRepository authorRepository; // dependency

	@GetMapping("/allbooks")
	List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@GetMapping("/books/search")
	ResponseEntity<?> searchBook( @RequestParam Optional<String> author,
			@RequestParam Optional<String> category,
			@RequestParam Optional<Integer> price,
			@RequestParam Optional<String> publisher) {
		Stream<Book> stream= bookRepository.findAll().stream();
		if(author.isPresent() ) {
			stream = stream.filter(book -> book.getAuthor().equals(author.get()));
		}
		if(category.isPresent()) {
			
			stream= stream.filter(book -> book.getCategory().equals(category.get()));
			//return ResponseEntity.ok(category.get()+stream.collect(Collectors.toList()));
		}
		
		if(price.isPresent()) {
			stream=stream.filter(book ->String.valueOf(book.getPrice()).equals(String.valueOf(price.get())));
		}
		if(publisher.isPresent()) {
			stream=stream.filter(book -> book.getPublisher().equals(publisher.get()));
		}
		List<Book> searchResult= stream.collect(Collectors.toList());
		
		if(searchResult.size()>0) {
			return new ResponseEntity<List<Book>>(searchResult, HttpStatus.FOUND);
		}
			
		return new ResponseEntity<String>("NO Books Found",HttpStatus.NOT_FOUND);
		
	}

	@PostMapping("/author/{authorid}/books")//Creating book
	ResponseEntity<?> createBook(@Valid @RequestBody Book book,@PathVariable("authorid") Long authorid) {

		
		Optional<Author> author= authorRepository.findById(authorid);
		if(authorRepository.existsById(authorid) ) {
			
				if(author.get().isLoginstatus()) {
					book.setAuthorid(authorid);
					bookRepository.save(book);//mock
					
					return ResponseEntity.ok("Book Created Successfully");
				}
				else {
					return ResponseEntity.badRequest().body("Please Login to Create Book");
				}
		}
		return new ResponseEntity<String>("Invalid Author to create book",HttpStatus.UNAUTHORIZED);
	}
	
	@PutMapping("/author/{authorid}/books/{bookid}")//updating book
	ResponseEntity<?> updateBook(@Valid @RequestBody Book book,
			@PathVariable("authorid") Long authorid, 
			@PathVariable("bookid") Long bookid){
		
		Optional<Author> author= authorRepository.findById(Long.valueOf(3));
		if(bookRepository.existsById(bookid) ) {
			
				if(author.get().isLoginstatus()) {
					book.setAuthorid(authorid);
					book.setId(bookid);
					bookRepository.save(book);//mock
					
					return ResponseEntity.ok("Book Updated Successfully");
				}
				else {
					return ResponseEntity.badRequest().body("Please Login to Update Book");
				}
		}
		return new ResponseEntity<String>("No book found to Update"+authorRepository.existsById(bookid),HttpStatus.UNAUTHORIZED);
		
	}
	
	@GetMapping("/books/{authorid}")
	List<Book> getBooksByAuthorId(@PathVariable("authorid") Long authorid){
		return bookRepository.findAllByAuthorid( authorid);
	}
}