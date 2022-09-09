package com.bookservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.bookservice.entity.*;
import com.bookservice.repository.AuthorRepository;
import com.bookservice.request.LoginRequest;


@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
	@InjectMocks LoginController logincontroller;
	@InjectMocks ErrorController errorcontroller;
	@Mock AuthorRepository authorRepository;
	
	
	public static LoginRequest SampleLoginRequest() {
		LoginRequest loginrequest = new LoginRequest();
		
		loginrequest.setAuthorname("abc");
		loginrequest.setPassword("abcd");
		return loginrequest;
	}
	public static Optional<Author> SampleAuthor() {
		Base64.Encoder encoder = Base64.getMimeEncoder();
		String password=encoder.encodeToString("abcd".getBytes());
		Optional<Author> author = Optional.ofNullable(new Author());
		author.get().setId(Long.valueOf(1));
		author.get().setAuthorname("abc");
		author.get().setAuthoremail("abc@gmail.com");
		author.get().setLoginstatus(false);
		author.get().setPassword(password);
		return author;
	}
	
	@Test
	void testAuthenticateAuthor() {
		LoginRequest loginrequest= SampleLoginRequest();
		Optional<Author> author= SampleAuthor();
		
		when(authorRepository.findByAuthorname(loginrequest.getAuthorname())).thenReturn(author);
		assertEquals(logincontroller.authenticateAuthor(loginrequest),ResponseEntity.ok("Author Login Success"+author.get().getId()));
		
		author.get().setAuthorname("abcd");//wrong author name
		when(authorRepository.findByAuthorname(loginrequest.getAuthorname())).thenReturn(Optional.empty());
		assertEquals(logincontroller.authenticateAuthor(loginrequest),ResponseEntity.badRequest().body("Error: Invalid Credentials"));
		
		author.get().setPassword("abcd");//wrong password
		when(authorRepository.findByAuthorname(loginrequest.getAuthorname())).thenReturn(author);
		assertEquals(logincontroller.authenticateAuthor(loginrequest),ResponseEntity.badRequest().body("Error: Invalid Credentials"));
		
		
		
	}
	
		
	
	@Test
	void testSignoutAuthor() {
		LoginRequest loginrequest= SampleLoginRequest();
		Optional<Author> author= SampleAuthor();
		author.get().setLoginstatus(true);
		when(authorRepository.findByAuthorname(loginrequest.getAuthorname())).thenReturn(author);
		assertEquals(logincontroller.signoutAuthor(loginrequest),ResponseEntity.ok("Author Signout Success"));
		
		author.get().setLoginstatus(false);
		when(authorRepository.findByAuthorname(loginrequest.getAuthorname())).thenReturn(author);
		assertEquals(logincontroller.signoutAuthor(loginrequest),ResponseEntity.badRequest().body("Error: Please signin first to signout"));
		
		when(authorRepository.findByAuthorname(loginrequest.getAuthorname())).thenReturn(author);
		assertEquals(logincontroller.signoutAuthor(loginrequest),ResponseEntity.badRequest().body("Error: Please signin first to signout"));
		
		when(authorRepository.findByAuthorname(loginrequest.getAuthorname())).thenReturn(Optional.empty());
		assertEquals(logincontroller.signoutAuthor(loginrequest),ResponseEntity.badRequest().body("Error: Please signin first to signout"));
		

	}
	
	
	@Test
	void testRegisterUser() {
		Optional<Author> author= SampleAuthor();
		//when(emailValidation.ValidateEmail(author.get().getAuthoremail())).thenReturn(true);
		when(authorRepository.existsByAuthorname(author.get().getAuthorname())).thenReturn(false);
		when(authorRepository.existsByAuthoremail(author.get().getAuthoremail())).thenReturn(false);
		assertEquals(logincontroller.signinAuthor(author.get()),ResponseEntity.ok("Author registered successfully! \n Your Author ID : "+author.get().getId()));
		
		when(authorRepository.existsByAuthorname(author.get().getAuthorname())).thenReturn(true);
		assertEquals(logincontroller.signinAuthor(author.get()),ResponseEntity
				.badRequest()
				.body("Author Name is already in use !"));
		
		when(authorRepository.existsByAuthorname(author.get().getAuthorname())).thenReturn(false
				);
		when(authorRepository.existsByAuthoremail(author.get().getAuthoremail())).thenReturn(true);
		assertEquals(logincontroller.signinAuthor(author.get()),ResponseEntity
				.badRequest()
				.body("Author Email is already in use!"));
		
		author.get().setAuthoremail("abc");
		assertEquals(logincontroller.signinAuthor(author.get()),ResponseEntity.badRequest().body("Please Enter a Valid Email "));
		
		
	}
	
	

	

//	@Test
//	void testHandleException() {
////		LoginRequest loginrequest= SampleLoginRequest();
////		Optional<Author> author= SampleAuthor();
////		loginrequest.setAuthorname("");
////		BindingResult br=new BindingResult();
////		br.getAllErrors().
////		//when(authorRepository.findByAuthorname(loginrequest.getAuthorname())).thenThrow(new MethodArgumentNotValidException(null,null));
////		Map<String,String> map= new HashMap<String,String>();
////		map.put("authorname", "must not be blank");
////		
////		assertEquals(logincontroller.authenticateAuthor(loginrequest),map);
//	}
//
//	@Test
//	void testRequestParamNotFoundHttpMessageNotReadableException() {
//		assertEquals(ErrorController.)
//	}
//
//	@Test
//	void testRequestParamNotFoundMissingServletRequestParameterException() {
//		fail("Not yet implemented");
//	}

}
