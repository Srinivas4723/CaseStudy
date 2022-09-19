package com.bookservice.controller;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bookservice.entity.Author;
import com.bookservice.repository.AuthorRepository;
import com.bookservice.request.LoginRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/digitalbooks/author")
public class LoginController extends BaseController {
	@Autowired AuthorRepository authorRepository;
	@Autowired AuthenticationManager authenticationManager;
	@Autowired  PasswordEncoder passwordEncoder;
	/**
	 * Authenticates Author and Author sign in
	 * @param loginRequest
	 * @return
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateAuthor(@Valid @RequestBody LoginRequest loginRequest) {
		Optional<Author> author= authorRepository.findByAuthorname(loginRequest.getAuthorname());
		if(author.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), author.get().getPassword())) {
				author.get().setLoginstatus(true);
				authorRepository.save(author.get());
				return ResponseEntity.ok("Author Login Success"+author.get().getId());
		}
	return  ResponseEntity.badRequest().body("Error: Invalid Credentials");
	}
	/**
	 * Author Sign out 
	 * @param loginRequest
	 * @return
	 */
	@PostMapping("/{authorid}/signout")
	public ResponseEntity<?> signoutAuthor(@PathVariable Long authorid) {
		Optional<Author> author= authorRepository.findById(authorid);
		if(author.isPresent() && author.get().isLoginstatus()) {
				author.get().setLoginstatus(false);
				authorRepository.save(author.get());
				return ResponseEntity.ok("Author Signout Success");
		}
		return  ResponseEntity.badRequest().body("Error: Please signin first, to signout");
	}
	
	/**
	 * Creates an Account for Author
	 * @param author
	 * @return
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> signupAuthor(@Valid @RequestBody Author author) {
		if (authorRepository.existsByAuthorname(author.getAuthorname())) {
			return ResponseEntity.badRequest().body("Author Name is already in use !");
		}
		if (authorRepository.existsByAuthoremail(author.getAuthoremail())) {
			return ResponseEntity.badRequest().body("Author Email is already in use!");
		}	
		author.setPassword(passwordEncoder.encode(author.getPassword()));
		authorRepository.save(author);
		return ResponseEntity.ok("Author registered successfully! \n Your Author ID : "+author.getId());
	}
}
