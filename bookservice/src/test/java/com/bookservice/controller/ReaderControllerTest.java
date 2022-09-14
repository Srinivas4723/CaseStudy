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
import com.bookservice.entity.PurchasedBooks;
import com.bookservice.entity.Reader;
import com.bookservice.repository.AuthorRepository;
import com.bookservice.repository.BookRepository;
import com.bookservice.repository.PurchasedBookRepository;
import com.bookservice.repository.ReaderRepository;
import com.bookservice.request.BuyBookRequest;

@ExtendWith(MockitoExtension.class)
class ReaderControllerTest {
	
	@InjectMocks ReaderController readerController;
	@Mock BookRepository bookRepository;
	@Mock AuthorRepository authorRepository;
	@Mock ReaderRepository readerRepository;
	@Mock PurchasedBookRepository purchasedBookRepository;
	
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
		return reader;
	}
	private PurchasedBooks SamplePurchasedBooks() {
		PurchasedBooks purchasedBook = new PurchasedBooks();
		purchasedBook.setBookid(Long.valueOf(1));
		purchasedBook.setReaderemail("abc@gmail.com");
		purchasedBook.setPaymentid("PID1234");
		return purchasedBook;
	}
	BuyBookRequest buybookrequest=SampleBuyBookRequest();
	Reader reader = SampleReader();
	Book book=SampleBook();
	PurchasedBooks purchasedBook=SamplePurchasedBooks();
	Long bookid=Long.valueOf(1);
	/**
	 * Buy Book Success By Existing Reader
	 */
	@Test
	void testBuyBooksSuccessByExistingReader() {
		purchasedBook.setBookid(Long.valueOf(2));
		when(bookRepository.existsById(buybookrequest.getBookid())).thenReturn(true);
		when(readerRepository.findByReadernameAndReaderemail(buybookrequest.getReadername(),buybookrequest.getReaderemail())).thenReturn(Optional.ofNullable(reader));
		when(readerRepository.findByReadernameOrReaderemail(buybookrequest.getReadername(),buybookrequest.getReaderemail())).thenReturn(Optional.ofNullable(reader));
		when(purchasedBookRepository.findAllByReaderemail(buybookrequest.getReaderemail())).thenReturn(Arrays.asList(purchasedBook));
		assertEquals(readerController.buyBooks(buybookrequest),ResponseEntity.ok("Book Purchase Successful \n Please note the Payment Id for your reference\nPayment Id : "+buybookrequest.getPaymentid().replace(",","")));
	}
	/**
	 * Buy Book Fail By book is already Purchased
	 */
	@Test
	void testBuyBooksFailByBookAlreadyPurchased() {
		when(bookRepository.existsById(buybookrequest.getBookid())).thenReturn(true);
		when(readerRepository.findByReadernameAndReaderemail(buybookrequest.getReadername(),buybookrequest.getReaderemail())).thenReturn(Optional.ofNullable(reader));
		when(readerRepository.findByReadernameOrReaderemail(buybookrequest.getReadername(),buybookrequest.getReaderemail())).thenReturn(Optional.ofNullable(reader));
		when(purchasedBookRepository.findAllByReaderemail(buybookrequest.getReaderemail())).thenReturn(Arrays.asList(purchasedBook));
		assertEquals(readerController.buyBooks(buybookrequest),ResponseEntity.badRequest().body("Book is already purchased"));
	}
	/**
	 * Buy Book Success By New Reader
	 */
	@Test
	void testBuyBooksSuccessByNewReader() {
		when(bookRepository.existsById(buybookrequest.getBookid())).thenReturn(true);
		when(readerRepository.findByReadernameAndReaderemail(buybookrequest.getReadername(),buybookrequest.getReaderemail())).thenReturn(Optional.empty());
		when(readerRepository.findByReadernameOrReaderemail(buybookrequest.getReadername(),buybookrequest.getReaderemail())).thenReturn(Optional.empty());
		assertEquals(readerController.buyBooks(buybookrequest),ResponseEntity.ok("Book Purchase Successful \n Please note the Payment Id for your reference\nPayment Id : "+buybookrequest.getPaymentid().replace(",","")));
		
	}
	/**
	 * Buy Book Fail By Existing reader or Email (but not both)
	 */
	@Test
	void testBuyBooksFailByExistingReaderOrEmail() {
		when(bookRepository.existsById(buybookrequest.getBookid())).thenReturn(true);
		when(readerRepository.findByReadernameAndReaderemail(buybookrequest.getReadername(),buybookrequest.getReaderemail())).thenReturn(Optional.empty());
		when(readerRepository.findByReadernameOrReaderemail(buybookrequest.getReadername(),buybookrequest.getReaderemail())).thenReturn(Optional.ofNullable(reader));
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
		when(readerRepository.findByreaderemail(reader.getReaderemail())).thenReturn(Optional.ofNullable(reader));
		when(purchasedBookRepository.findAllByReaderemail(reader.getReaderemail())).thenReturn(Arrays.asList(purchasedBook));
		when(bookRepository.findAllById(Arrays.asList(Long.valueOf(1)))).thenReturn(books);
		assertEquals(readerController.getPurchasedBooks(reader.getReaderemail()),ResponseEntity.ok(books));
	}
	/**
	 * Get Purchased Books of Reader for Zero Books
	 */
	@Test
	void testGetPurchasedBooksForZeroBooks() {
		when(readerRepository.findByreaderemail(reader.getReaderemail())).thenReturn(Optional.ofNullable(reader));
		assertEquals(readerController.getPurchasedBooks(reader.getReaderemail()),ResponseEntity.badRequest().body("No Books Purchased"));
	}
	/**
	 * Get Purchased Books Fail By Invalid Mail
	 */
	@Test
	void testGetPurchasedBooksFailByInvalidMail() {	
		when(readerRepository.findByreaderemail(reader.getReaderemail())).thenReturn(Optional.empty());
		assertEquals(readerController.getPurchasedBooks(reader.getReaderemail()),ResponseEntity.badRequest().body("No books Purchased with this email"));
	}
	/**
	 * Get Book By BookId Fail By Invalid Reader
	 */
	@Test
	void testGetBookContentFailByInvalidReader() {
		purchasedBook.setBookid(Long.valueOf(2));
		when(readerRepository.findByreaderemail(reader.getReaderemail())).thenReturn(Optional.ofNullable(reader));
		when(purchasedBookRepository.findAllByReaderemail(reader.getReaderemail())).thenReturn(Arrays.asList(purchasedBook));
		assertEquals(readerController.getBookContent(reader.getReaderemail(), bookid),new ResponseEntity<String>("Unauthorised to read book / Invalid Book",HttpStatus.NOT_FOUND));
	}
	@Test
	void testGetBookContentSuccess() {
		when(readerRepository.findByreaderemail(reader.getReaderemail())).thenReturn(Optional.ofNullable(reader));
		when(purchasedBookRepository.findAllByReaderemail(reader.getReaderemail())).thenReturn(Arrays.asList(purchasedBook));
		when(bookRepository.findById(bookid)).thenReturn(Optional.ofNullable(book));
		assertEquals(readerController.getBookContent(reader.getReaderemail(), bookid),ResponseEntity.ok(Optional.ofNullable(book)));
	}
	@Test
	void testGetBookContentInvalidEmail() {
		when(readerRepository.findByreaderemail(reader.getReaderemail())).thenReturn(Optional.empty());
		assertEquals(readerController.getBookContent(reader.getReaderemail(), bookid),ResponseEntity.badRequest().body("Email Doesn't Exist"));
	}
	/**
	 * Get Books By Payment Id Success
	 */
	@Test
	void testGetBookByPaymentIdSuccess() {
		String paymentid="PID1234";
		//reader.setPurchasedbooks("1,2");
		//reader.setPaymentid("PID1234,PID12345");
		when(readerRepository.findByreaderemail(reader.getReaderemail())).thenReturn(Optional.ofNullable(reader));
		when(purchasedBookRepository.findByPaymentid(paymentid)).thenReturn(Optional.ofNullable(purchasedBook));
		when(bookRepository.findById(Long.valueOf(1))).thenReturn(Optional.ofNullable(book));
		assertEquals(readerController.getBookByPaymentId(reader.getReaderemail(), paymentid),ResponseEntity.ok(Optional.ofNullable(book)));
	}
	@Test
	void testGetBookByPaymentIdFailByInvalidPaymentId() {
		String paymentid="PID1234";
		//reader.setPaymentid("PID12345,PID123456");
		when(readerRepository.findByreaderemail(reader.getReaderemail())).thenReturn(Optional.ofNullable(reader));
	
		assertEquals(readerController.getBookByPaymentId(reader.getReaderemail(), paymentid),new ResponseEntity<String>("Invalid Payment ID",HttpStatus.NOT_FOUND));
	}
	@Test
	void testGetBookByPaymentIdFailByInvalidEmail() {
		String paymentid="PID1234";
		when(readerRepository.findByreaderemail(reader.getReaderemail())).thenReturn(Optional.empty());
		assertEquals(readerController.getBookByPaymentId(reader.getReaderemail(), paymentid),ResponseEntity.badRequest().body("Email Doesn't Exist"));
	}

	@Test
	void testGetRefundBookByBookId() {
		//reader.setPurchasedbooks("1,2");
		//reader.setPaymentid("PID1234,PID12345");
		when(readerRepository.findByreaderemail(reader.getReaderemail())).thenReturn(Optional.ofNullable(reader));
		when(purchasedBookRepository.findByReaderemailAndBookid(purchasedBook.getReaderemail(),bookid)).thenReturn(Optional.ofNullable(purchasedBook));
		assertEquals(readerController.getRefundBookByBookId(reader.getReaderemail(), bookid),new ResponseEntity<String>("Book Unpurchased, refund will be credited shortly",HttpStatus.OK));
	}
	@Test
	void testGetRefundBookByBookIdFailByNotPurchased() {
		when(readerRepository.findByreaderemail(reader.getReaderemail())).thenReturn(Optional.ofNullable(reader));
		assertEquals(readerController.getRefundBookByBookId(reader.getReaderemail(), bookid),new ResponseEntity<String>("Book Not purchased  to return",HttpStatus.NOT_FOUND));
	}
	@Test
	void testGetRefundBookByBookIdFailByInvalidEmai() {
		when(readerRepository.findByreaderemail(reader.getReaderemail())).thenReturn(Optional.empty());
		assertEquals(readerController.getRefundBookByBookId(reader.getReaderemail(), bookid),ResponseEntity.badRequest().body("Email Doesn't Exist"));
		
	}

}
