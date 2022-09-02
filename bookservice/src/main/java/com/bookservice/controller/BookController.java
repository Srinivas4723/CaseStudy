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
import com.bookservice.service.BookService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController // spring bean
@RequestMapping("/api/v1/digitalbooks")
public class BookController extends ErrorController {// accept requests
	@Autowired // DI
	BookRepository bookRepository; // dependency
	
	@Autowired // DI
	AuthorRepository authorRepository; // dependency

	@GetMapping
	Iterable<Book> getUser() {
		return bookRepository.findAll();
	}

	@GetMapping("/books/search")
	ResponseEntity<?> getBook( @RequestParam(required=false) Optional<String> author,
			@RequestParam(required=false) Optional<String> category,
			@RequestParam(required=false) Optional<Integer> price,
			@RequestParam(required=false) Optional<String> publisher) {
		Stream<Book> stream= bookRepository.findAll().stream();
		if(author.isPresent() ) {
			stream = stream.filter(book -> book.getAuthor().equals(author.get()));
		}
		if(category.isPresent()) {
			stream= stream.filter(book -> book.getCategory().equals(category.get()));
		}
		if(price.isPresent()) {
			stream=stream.filter(book ->book.getPrice()==price.get());
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

	@PostMapping("/author/{authorid}/books")
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
	
	@PutMapping("/author/{authorid}/books/{bookid}")
	ResponseEntity<?> updateBook(@Valid @RequestBody Book book,
			@PathVariable("authorid") Long authorid, 
			@PathVariable("bookid") Long bookid){
		
		Optional<Author> author= authorRepository.findById(authorid);
		if(authorRepository.existsById(bookid) ) {
			
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
		return new ResponseEntity("No book found to Update",HttpStatus.UNAUTHORIZED);
		
	}
}