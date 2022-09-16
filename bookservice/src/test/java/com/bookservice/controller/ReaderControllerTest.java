package com.bookservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
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
import com.bookservice.entity.Category;
import com.bookservice.entity.Reader;
import com.bookservice.repository.AuthorRepository;
import com.bookservice.repository.BookRepository;
import com.bookservice.repository.ReaderRepository;
import com.bookservice.request.BuyBookRequest;

@ExtendWith(MockitoExtension.class)
class ReaderControllerTest {
	
	@InjectMocks ReaderController readerController;
	@Mock BookRepository bookRepository;
	@Mock AuthorRepository authorRepository;
	@Mock ReaderRepository readerRepository;
	
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
		book.setContent("content");
		return book;
	}
	private BuyBookRequest SampleBuyBookRequest() {
		BuyBookRequest buyBookRequest = new BuyBookRequest();
		buyBookRequest.setBookid(Long.valueOf(1));
		buyBookRequest.setReadername("abc");
		buyBookRequest.setReaderemail("abc@gmail.com");
		return buyBookRequest;
	}
	private Reader SampleReader() {
		Reader reader = new Reader();
		reader.setId(Long.valueOf(1));
		reader.setReadername("abc");
		reader.setReaderemail("abc@gmail.com");
		reader.setBookid(Long.valueOf(1));
		reader.setPaymentid("PID1234");
		return reader;
	}
	BuyBookRequest buybookrequest=SampleBuyBookRequest();
	Reader reader = SampleReader();
	Book book=SampleBook();
	Long bookid=Long.valueOf(1);
	/**
	 * Buy Book Success By Existing Reader
	 */
	@Test
	void testBuyBooksSuccessByExistingReader() {
		buybookrequest.setBookid(Long.valueOf(2));
		when(bookRepository.existsById(buybookrequest.getBookid())).thenReturn(true);
		when(readerRepository.findByReadernameAndReaderemail(buybookrequest.getReadername(),buybookrequest.getReaderemail())).thenReturn(Arrays.asList(reader));
		when(readerRepository.findByReadernameOrReaderemail(buybookrequest.getReadername(),buybookrequest.getReaderemail())).thenReturn(Arrays.asList(reader));
		assertEquals(readerController.buyBooks(buybookrequest),ResponseEntity.ok("Book Purchase Successful \n Please note the Payment Id for your reference\nPayment Id : "+buybookrequest.getPaymentid().replace(",","")));
	}
	/**
	 * Buy Book Fail By book is already Purchased
	 */
	@Test
	void testBuyBooksFailByBookAlreadyPurchased() {
		when(bookRepository.existsById(buybookrequest.getBookid())).thenReturn(true);
		when(readerRepository.findByReadernameAndReaderemail(buybookrequest.getReadername(),buybookrequest.getReaderemail())).thenReturn(Arrays.asList(reader));
		when(readerRepository.findByReadernameOrReaderemail(buybookrequest.getReadername(),buybookrequest.getReaderemail())).thenReturn(Arrays.asList(reader));
		assertEquals(readerController.buyBooks(buybookrequest),ResponseEntity.badRequest().body("Book is already purchased"));
	}
	/**
	 * Buy Book Success By New Reader
	 */
	@Test
	void testBuyBooksSuccessByNewReader() {
		when(bookRepository.existsById(buybookrequest.getBookid())).thenReturn(true);
//		when(readerRepository.findByReadernameAndReaderemail(buybookrequest.getReadername(),buybookrequest.getReaderemail())).thenReturn(Arrays.asList());
//		when(readerRepository.findByReadernameOrReaderemail(buybookrequest.getReadername(),buybookrequest.getReaderemail())).thenReturn(Optional.empty());
		assertEquals(readerController.buyBooks(buybookrequest),ResponseEntity.ok("Book Purchase Successful \n Please note the Payment Id for your reference\nPayment Id : "+buybookrequest.getPaymentid().replace(",","")));
		
	}
	/**
	 * Buy Book Fail By Existing reader or Email (but not both)
	 */
	@Test
	void testBuyBooksFailByExistingReaderOrEmail() {
		when(bookRepository.existsById(buybookrequest.getBookid())).thenReturn(true);
		when(readerRepository.findByReadernameAndReaderemail(buybookrequest.getReadername(),buybookrequest.getReaderemail())).thenReturn(Arrays.asList());
		when(readerRepository.findByReadernameOrReaderemail(buybookrequest.getReadername(),buybookrequest.getReaderemail())).thenReturn(Arrays.asList(reader));
		assertEquals(readerController.buyBooks(buybookrequest),ResponseEntity.badRequest().body("User/Email has already exists,\n Please Try with different one's"));
	}
	/**
	 * Buy Book Fail By Invalid Book Id
	 */
	@Test
	void testBuyBooksFailByInvalidBook() {
		when(bookRepository.existsById(buybookrequest.getBookid())).thenReturn(false);
		assertEquals(readerController.buyBooks(buybookrequest),ResponseEntity.badRequest().body("Invalid Book Id / Book not available"));
	}
	/**
	 * Get Purchased Books of Reader
	 */
	@Test
	void testGetPurchasedBooks() {
		List<Book> books=new ArrayList<Book>();
		books.add(book);
		when(readerRepository.findAllByReaderemail(reader.getReaderemail())).thenReturn(Arrays.asList(reader));
		when(bookRepository.findAllById(Arrays.asList(Long.valueOf(1)))).thenReturn(books);
		assertEquals(readerController.getPurchasedBooks(reader.getReaderemail()),ResponseEntity.ok(books));
	}
	/**
	 * Get Purchased Books of Reader for Zero Books
	 */
	@Test
	void testGetPurchasedBooksForZeroBooks() {
		assertEquals(readerController.getPurchasedBooks(reader.getReaderemail()),ResponseEntity.badRequest().body("Invalid Email Id / No Books Purchased"));
	}
	/**
	 * Get Purchased Books Fail By Invalid Mail
	 */
	@Test
	void testGetPurchasedBooksFailByInvalidMail() {	
		when(readerRepository.findAllByReaderemail(reader.getReaderemail())).thenReturn(Arrays.asList());
		assertEquals(readerController.getPurchasedBooks(reader.getReaderemail()),ResponseEntity.badRequest().body("Invalid Email Id / No Books Purchased"));
	}
	/**
	 * Get Book By BookId Fail By Invalid Reader or Book
	 */
	@Test
	void testGetBookContentFailByInvalidReaderOrBook() {
		when(readerRepository.findByReaderemailAndBookid(buybookrequest.getReaderemail(),buybookrequest.getBookid())).thenReturn(Optional.empty());
		assertEquals(readerController.getBookContent(reader.getReaderemail(), bookid),ResponseEntity.badRequest().body("Invalid Email / Book"));
	}
	@Test
	void testGetBookContentSuccess() {
		when(readerRepository.findByReaderemailAndBookid(buybookrequest.getReaderemail(),buybookrequest.getBookid())).thenReturn(Optional.ofNullable(reader));
		when(bookRepository.findById(bookid)).thenReturn(Optional.ofNullable(book));
		assertEquals(readerController.getBookContent(reader.getReaderemail(), bookid),ResponseEntity.ok(Optional.ofNullable(book)));
	}
	/**
	 * Get Books By Payment Id Success
	 */
	@Test
	void testGetBookByPaymentIdSuccess() {
		String paymentid="PID1234";
		when(readerRepository.findByReaderemailAndPaymentid(reader.getReaderemail(),paymentid)).thenReturn(Optional.ofNullable(reader));
		when(bookRepository.findById(Long.valueOf(1))).thenReturn(Optional.ofNullable(book));
		assertEquals(readerController.getBookByPaymentId(reader.getReaderemail(), paymentid),ResponseEntity.ok(Optional.ofNullable(book)));
	}
	@Test
	void testGetBookByPaymentIdFailByInvalidEmailOrPaymentId() {
		String paymentid="PID1234";
		when(readerRepository.findByReaderemailAndPaymentid(reader.getReaderemail(),paymentid)).thenReturn(Optional.empty());
		assertEquals(readerController.getBookByPaymentId(reader.getReaderemail(), paymentid),ResponseEntity.badRequest().body("Invalid Email / Payment ID"));
	}
	@Test
	void testGetRefundBookByBookId() {
		when(readerRepository.findByReaderemailAndBookid(reader.getReaderemail(),buybookrequest.getBookid())).thenReturn(Optional.ofNullable(reader));
		assertEquals(readerController.getRefundBookByBookId(reader.getReaderemail(), bookid),new ResponseEntity<String>("Book Unpurchased, refund will be credited shortly",HttpStatus.OK));
	}
	@Test
	void testGetRefundBookByBookIdFailByInvalidEmail() {
		when(readerRepository.findByReaderemailAndBookid(reader.getReaderemail(),buybookrequest.getBookid())).thenReturn(Optional.empty());
		assertEquals(readerController.getRefundBookByBookId(reader.getReaderemail(), bookid),new ResponseEntity<>("Invalid Email Id / Book",HttpStatus.NOT_FOUND));
		
	}

}
