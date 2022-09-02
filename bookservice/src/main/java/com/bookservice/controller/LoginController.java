package com.bookservice.controller;

import java.util.Base64;
import java.util.Optional;

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
	
	Base64.Encoder encoder = Base64.getMimeEncoder();

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
		Optional<Author> author= authorRepository.findByAuthorname(loginRequest.getAuthorname());
		if(author.isPresent() && 
				author.get().getPassword().equals(encoder.encodeToString(loginRequest.getPassword().getBytes()))) {
			author.get().setLoginstatus(true);
			authorRepository.save(author.get());
			return ResponseEntity.ok("Author Login Success");
		}
	return  ResponseEntity
			.badRequest()
			.body("Error: Invalid Credentials");
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody Author author) {
		
		if (authorRepository.existsByAuthorname(author.getAuthorname())) {
			return ResponseEntity
					.badRequest()
					.body("Error: Username is already taken!");
		}

		if (authorRepository.existsByAuthoremail(author.getAuthoremail())) {
			return ResponseEntity
					.badRequest()
					.body("Error: Email is already in use!");
		}

		// Create new user's account
		
		author.setPassword( encoder.encodeToString(author.getPassword().getBytes()));
									
		authorRepository.save(author);

		return ResponseEntity.ok("Author registered successfully! \n Your Author ID : "+author.getId());
	}
}
