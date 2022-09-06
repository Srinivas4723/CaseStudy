package com.bookservice.controller;

import java.util.Base64;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookservice.entity.Author;
import com.bookservice.repository.AuthorRepository;
import com.bookservice.request.LoginRequest;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/digitalbooks/author")
public class LoginController extends ErrorController {
	
	@Autowired
	AuthorRepository authorRepository;
	
	private boolean ValidateEmail(String email) {
		String regex="^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(email).matches();
	}
	
	Base64.Encoder encoder = Base64.getMimeEncoder();

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateAuthor(@Valid @RequestBody LoginRequest loginRequest) {
		
		Optional<Author> author= authorRepository.findByAuthorname(loginRequest.getAuthorname());
		
		if(author.isPresent() ) { 
				if(author.get().getPassword().equals(encoder.encodeToString(loginRequest.getPassword().getBytes()))) {
					author.get().setLoginstatus(true);
					authorRepository.save(author.get());
					return ResponseEntity.ok("Author Login Success"+author.get().getId());
			}
		}
	return  ResponseEntity
			.badRequest()
			.body("Error: Invalid Credentials");
	}
	@PostMapping("/signout")
	public ResponseEntity<?> signoutAuthor(@Valid @RequestBody LoginRequest loginRequest) {
		
		Optional<Author> author= authorRepository.findByAuthorname(loginRequest.getAuthorname());
		if(author.isPresent()) {
			if(author.get().isLoginstatus()) {
				
				author.get().setAuthorname("abc1");
				authorRepository.save(author.get());
				return ResponseEntity.ok("Author Signout Success"+author.get().getAuthorname());
			}
		}
	return  ResponseEntity
			.badRequest()
			.body("Error: Please signin first to signout");
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signinAuthor(@Valid @RequestBody Author author) {
		
	 if(ValidateEmail(author.getAuthoremail())) {
		if (authorRepository.existsByAuthorname(author.getAuthorname())) {
			return ResponseEntity
					.badRequest()
					.body("Author Name is already in use !");
		}

		if (authorRepository.existsByAuthoremail(author.getAuthoremail())) {
			return ResponseEntity
					.badRequest()
					.body("Author Email is already in use!");
		}

		// Create new user's account
		
		author.setPassword( encoder.encodeToString(author.getPassword().getBytes()));
									
		authorRepository.save(author);

		return ResponseEntity.ok("Author registered successfully! \n Your Author ID : "+author.getId());
		}
	 else {
		 return ResponseEntity.badRequest().body("Please Enter a Valid Email ");
	 }
		
	}
}
