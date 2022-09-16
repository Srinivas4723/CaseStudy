package com.bookservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.bookservice.entity.Author;
import com.bookservice.repository.AuthorRepository;
import com.bookservice.request.LoginRequest;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
	
	@InjectMocks LoginController loginController;
	@InjectMocks BaseController baseController;
	@Mock AuthorRepository authorRepository;
	@Mock PasswordEncoder passwordEncoder;
	public static LoginRequest SampleLoginRequest() {
		LoginRequest loginrequest = new LoginRequest();
		loginrequest.setAuthorname("abc");
		loginrequest.setPassword("abc");
		return loginrequest;
	}
	
	public static Optional<Author> SampleAuthor() {
		Optional<Author> author = Optional.ofNullable(new Author());
		author.get().setId(Long.valueOf(1));
		author.get().setAuthorname("abc");
		author.get().setAuthoremail("abc@gmail.com");
		author.get().setLoginstatus(false);
		author.get().setPassword("abc");
		return author;
	}
	LoginRequest loginRequest= SampleLoginRequest();
	Optional<Author> author= SampleAuthor();
	/**
	 * SignIn Success Test Case
	 */
	@Test
	void testAuthenticateAuthorSignInSuccess() {
		when(passwordEncoder.matches(loginRequest.getPassword(), author.get().getPassword())).thenReturn(true);
		when(authorRepository.findByAuthorname(loginRequest.getAuthorname())).thenReturn(author);
		assertEquals(loginController.authenticateAuthor(loginRequest),ResponseEntity.ok("Author Login Success"+author.get().getId()));
	}
	/**
	 * SignIn Fail By wrong author name
	 */
	@Test
	void testAuthenticateAuthorFailByWrongAuthorName() {
		author.get().setAuthorname("abcd");
		when(authorRepository.findByAuthorname(loginRequest.getAuthorname())).thenReturn(Optional.empty());
		assertEquals(loginController.authenticateAuthor(loginRequest),ResponseEntity.badRequest().body("Error: Invalid Credentials"));
	}
	/**
	 * SignIn Fail By Wrong Password
	 */
	@Test
	void testAuthenticateAuthorFailByInvalidPassword() {
		author.get().setPassword("abcd");//wrong password
		when(authorRepository.findByAuthorname(loginRequest.getAuthorname())).thenReturn(author);
		assertEquals(loginController.authenticateAuthor(loginRequest),ResponseEntity.badRequest().body("Error: Invalid Credentials"));
	}
	
	/**
	 * Sign out Success Case
	 */
	@Test
	void testSignoutAuthor() {
		author.get().setLoginstatus(true);
		when(authorRepository.findById(author.get().getId())).thenReturn(author);
		assertEquals(loginController.signoutAuthor(author.get().getId()),ResponseEntity.ok("Author Signout Success"));
	}
	/**
	 * Sign out Fail By Not LoggedIn
	 */
	@Test 
	void testSignoutAuthorFailByNotLoggedIn(){
		author.get().setLoginstatus(false);
		when(authorRepository.findById(author.get().getId())).thenReturn(author);
		assertEquals(loginController.signoutAuthor(author.get().getId()),ResponseEntity.badRequest().body("Error: Please signin first, to signout"));
		
	}
	/**
	 * Signout Fail By Author not Exists
	 */
	@Test
	void testSignoutAuthorFailByAuthorNotExists(){
		when(authorRepository.findById(author.get().getId())).thenReturn(Optional.empty());
		assertEquals(loginController.signoutAuthor(author.get().getId()),ResponseEntity.badRequest().body("Error: Please signin first, to signout"));
		
	}
	/**
	 * SignUp Success
	 */
	@Test
	void testSignupAuthorSuccess() {
		when(authorRepository.existsByAuthorname(author.get().getAuthorname())).thenReturn(false);
		when(authorRepository.existsByAuthoremail(author.get().getAuthoremail())).thenReturn(false);
		assertEquals(loginController.signupAuthor(author.get()),ResponseEntity.ok("Author registered successfully! \n Your Author ID : "+author.get().getId()));
	}
	/**
	 * SignUp Fail By Author Name Exists
	 */
	@Test
	void testSignupAuthorFailByAuthorNameExists() {
		when(authorRepository.existsByAuthorname(author.get().getAuthorname())).thenReturn(true);
		assertEquals(loginController.signupAuthor(author.get()),ResponseEntity.badRequest().body("Author Name is already in use !"));
	}
	/**
	 * Signup Fail By Author Email Exists
	 */
	@Test
	void testSignupAuthorFailByAuthorEmailExists() {
		when(authorRepository.existsByAuthorname(author.get().getAuthorname())).thenReturn(false);
		when(authorRepository.existsByAuthoremail(author.get().getAuthoremail())).thenReturn(true);
		assertEquals(loginController.signupAuthor(author.get()),ResponseEntity.badRequest().body("Author Email is already in use!"));
	}
}
