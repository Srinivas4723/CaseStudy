package com.bookservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bookservice.entity.Book;
import com.bookservice.entity.Reader;
import com.bookservice.repository.AuthorRepository;
import com.bookservice.repository.BookRepository;
import com.bookservice.repository.ReaderRepository;
import com.bookservice.request.BuyBookRequest;
@ExtendWith(MockitoExtension.class)
class ReaderControllerTest {
	@InjectMocks ReaderController readerController;
	@Mock BookRepository bookrepository;
	@Mock AuthorRepository authorRepository;
	@Mock ReaderRepository readerrepository;
	
	public static Book SampleBook() {
		Book book = new Book(Long.valueOf(1),Long.valueOf(1),"title","category",500,"author","publisher","27/03/2022",5,true);
		return book;
	}
	private BuyBookRequest SampleBuyBookRequest() {
		BuyBookRequest buybookrequest = new BuyBookRequest(Long.valueOf(1),"abc","abc@gmail.com");
		return buybookrequest;
	}
	private Reader SampleReader() {
		Reader reader = new Reader();
		reader.setId(Long.valueOf(1));
		reader.setReadername("abc");
		reader.setReaderemail("abc@gmail.com");
		reader.setBooks("");
		return reader;
	}
	@Test
	void testBuyBooks() {
		BuyBookRequest buybookrequest=SampleBuyBookRequest();
		Reader reader = SampleReader();
		when(bookrepository.existsById(buybookrequest.getBookid())).thenReturn(true);
		reader.setBooks("");
		when(readerrepository.findByreadername(buybookrequest.getReadername())).thenReturn(Optional.ofNullable(reader));
		when(readerrepository.findByreaderemail(buybookrequest.getReaderemail())).thenReturn(Optional.ofNullable(reader));
		
		assertEquals(readerController.buyBooks(buybookrequest),ResponseEntity.ok("Book Purchase Successful \n Please note the Payment Id for your reference\nPayment Id : "+reader.getPaymentid().replace(",","")));
		
		assertEquals(readerController.buyBooks(buybookrequest),ResponseEntity.badRequest().body("Book is already purchased"));
		
		
		buybookrequest.setBookid(Long.valueOf(2));
		when(bookrepository.existsById(buybookrequest.getBookid())).thenReturn(true);
		assertEquals(readerController.buyBooks(buybookrequest),ResponseEntity.ok("Book Purchase Successful \n Please note the Payment Id for your reference\nPayment Id : "+buybookrequest.getPid().replace(",","")));
		
		reader.setReaderemail("abc1@gmail.com");
		when(readerrepository.findByreaderemail(buybookrequest.getReaderemail())).thenReturn(Optional.ofNullable(reader));
		assertEquals(readerController.buyBooks(buybookrequest),ResponseEntity.badRequest().body("User/Email has already exists,\n Please Try with different one's"));
		
		when(readerrepository.findByreadername(buybookrequest.getReadername())).thenReturn(Optional.ofNullable(reader));
		when(readerrepository.findByreaderemail(buybookrequest.getReaderemail())).thenReturn(Optional.empty());
		assertEquals(readerController.buyBooks(buybookrequest),ResponseEntity.badRequest().body("User/Email has already exists,\n Please Try with different one's"));
				
		when(readerrepository.findByreadername(buybookrequest.getReadername())).thenReturn(Optional.empty());
		when(readerrepository.findByreaderemail(buybookrequest.getReaderemail())).thenReturn(Optional.ofNullable(reader));
		assertEquals(readerController.buyBooks(buybookrequest),ResponseEntity.badRequest().body("User/Email has already exists,\n Please Try with different one's"));		
		
		when(readerrepository.findByreadername(buybookrequest.getReadername())).thenReturn(Optional.empty());
		when(readerrepository.findByreaderemail(buybookrequest.getReaderemail())).thenReturn(Optional.empty());
		assertEquals(readerController.buyBooks(buybookrequest),ResponseEntity.ok("Book Purchase Successful \n Please note the Payment Id for your reference\nPayment Id : "+buybookrequest.getPid()));
		
		
		
		when(bookrepository.existsById(buybookrequest.getBookid())).thenReturn(false);
		assertEquals(readerController.buyBooks(buybookrequest),ResponseEntity.badRequest().body("Invalid Book Id / Book not available"));
	}

	

	

	@Test
	void testGetPurchasedBooks() {
		String emailid="abc@gmail.com";
		Reader reader = SampleReader();
		reader.setBooks("1,");
		Book book = SampleBook();
		List<Book> books=new ArrayList<Book>();
		books.add(book);
		when(readerrepository.findByreaderemail(emailid)).thenReturn(Optional.ofNullable(reader));
		when(bookrepository.findAllById(Arrays.asList(Long.valueOf(1)))).thenReturn(books);
		assertEquals(readerController.getPurchasedBooks(emailid),ResponseEntity.ok(books));
		
		when(readerrepository.findByreaderemail(emailid)).thenReturn(Optional.empty());
		assertEquals(readerController.getPurchasedBooks(emailid),ResponseEntity.badRequest().body("Email Doesn't Exist"));
	}

	@Test
	void testGetBookContent() {
		String emailid="abc@gmail.com";
		Long bookid=Long.valueOf(1);
		Reader reader = SampleReader();
		Book book = SampleBook();
		when(readerrepository.findByreaderemail(emailid)).thenReturn(Optional.ofNullable(reader));
		when(bookrepository.findById(bookid)).thenReturn(Optional.ofNullable(book));
		assertEquals(readerController.getBookContent(emailid, bookid),new ResponseEntity<String>("Unauthorised to read book",HttpStatus.NOT_FOUND));
		
		reader.setBooks("1,");
		assertEquals(readerController.getBookContent(emailid, bookid),ResponseEntity.ok(book));
		
		reader.setBooks("2,3");
		when(readerrepository.findByreaderemail(emailid)).thenReturn(Optional.ofNullable(reader));
		assertEquals(readerController.getBookContent(emailid, bookid),new ResponseEntity<String>("Unauthorised to read book",HttpStatus.NOT_FOUND));
		
		when(bookrepository.findById(bookid)).thenReturn(Optional.empty());
		assertEquals(readerController.getBookContent(emailid, bookid),new ResponseEntity<String>("Unauthorised to read book",HttpStatus.NOT_FOUND));
		
		reader.setBooks("2,3");
		when(readerrepository.findByreaderemail(emailid)).thenReturn(Optional.ofNullable(reader));
		assertEquals(readerController.getBookContent(emailid, bookid),new ResponseEntity<String>("Unauthorised to read book",HttpStatus.NOT_FOUND));
		
		when(readerrepository.findByreaderemail(emailid)).thenReturn(Optional.empty());
		assertEquals(readerController.getBookContent(emailid, bookid),ResponseEntity.badRequest().body("Email Doesn't Exist"));
	}

	@Test
	void testGetBookByPID() {
		String emailid="abc@gmail.com";
		String pid="PID1234";
		Reader reader= SampleReader();
		Book book = SampleBook();
		reader.setBooks("1,2");
		reader.setPaymentid("PID1234,PID12345");
		when(readerrepository.findByreaderemail(emailid)).thenReturn(Optional.ofNullable(reader));
		when(bookrepository.findById(Long.valueOf(1))).thenReturn(Optional.ofNullable(book));
		assertEquals(readerController.getBookByPID(emailid, pid),ResponseEntity.ok(Optional.ofNullable(book)));
		
		reader.setPaymentid("PID12345,PID123456");
		when(readerrepository.findByreaderemail(emailid)).thenReturn(Optional.ofNullable(reader));
	
		assertEquals(readerController.getBookByPID(emailid, pid),new ResponseEntity<String>("Invalid Payment ID",HttpStatus.NOT_FOUND));
		
		when(readerrepository.findByreaderemail(emailid)).thenReturn(Optional.empty());
		assertEquals(readerController.getBookByPID(emailid, pid),ResponseEntity.badRequest().body("Email Doesn't Exist"));
	}

	@Test
	void testGetRefundBookByBookId() {
		String emailid="abc@gmail.com";
		String bookid="1";
		Reader reader= SampleReader();
		reader.setBooks("1,2");
		reader.setPaymentid("PID1234,PID12345");
		when(readerrepository.findByreaderemail(emailid)).thenReturn(Optional.ofNullable(reader));
		assertEquals(readerController.getRefundBookByBookId(emailid, bookid),new ResponseEntity<String>("Book Unpurchased, refund will be credited shortly",HttpStatus.OK));
		
		reader.setBooks("2,3");
		reader.setPaymentid("PID1234,PID12345");
		when(readerrepository.findByreaderemail(emailid)).thenReturn(Optional.ofNullable(reader));
		assertEquals(readerController.getRefundBookByBookId(emailid, bookid),new ResponseEntity<String>("Book Not purchased  to return",HttpStatus.NOT_FOUND));
		
		when(readerrepository.findByreaderemail(emailid)).thenReturn(Optional.empty());
		assertEquals(readerController.getRefundBookByBookId(emailid, bookid),ResponseEntity.badRequest().body("Email Doesn't Exist"));
		
	}

}
