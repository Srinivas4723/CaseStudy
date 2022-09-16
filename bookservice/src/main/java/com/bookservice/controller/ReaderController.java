package com.bookservice.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bookservice.entity.Reader;
import com.bookservice.repository.BookRepository;
import com.bookservice.repository.ReaderRepository;
import com.bookservice.request.BuyBookRequest;

@CrossOrigin
@RestController
@RequestMapping("/digitalbooks")
public class ReaderController extends BaseController {
	@Autowired ReaderRepository readerRepository;
	@Autowired BookRepository bookRepository;
	static Random random = new Random();
	/**
	 * Generates PID and purchases a book
	 * @param buyBookRequest
	 */
	private  String purchaseBook(BuyBookRequest buyBookRequest) {
		Reader reader = new Reader();
		reader.setReadername(buyBookRequest.getReadername());
		reader.setReaderemail(buyBookRequest.getReaderemail());
		String paymentid="PID"+buyBookRequest.getBookid()+"2022"+random.nextInt(10000);
		reader.setBookid(buyBookRequest.getBookid());
		buyBookRequest.setPaymentid(paymentid);
		reader.setPaymentid(paymentid);
		readerRepository.save(reader);
		return paymentid;
	}
	/**
	 * Reader can buy a book 
	 * @param buybookrequest
	 * @return
	 */
	@PostMapping("/books/buy")
	ResponseEntity<String> buyBooks(@Valid @RequestBody BuyBookRequest buybookrequest){
		if(bookRepository.existsById(buybookrequest.getBookid())){
			List<Reader> reader1= readerRepository.findByReadernameAndReaderemail(buybookrequest.getReadername(),buybookrequest.getReaderemail());
			List<Reader> reader2= readerRepository.findByReadernameOrReaderemail(buybookrequest.getReadername(),buybookrequest.getReaderemail());
			if(!reader1.isEmpty()) {
				List<Long> purchasedbookids=reader1.stream().map(Reader::getBookid).collect(Collectors.toList());
				if(!purchasedbookids.contains(buybookrequest.getBookid())) {
					String paymentid=purchaseBook(buybookrequest);
					return ResponseEntity.ok("Book Purchase Successful \n Please note the Payment Id for your reference\nPayment Id : "+paymentid);
				}
				else {
					return ResponseEntity.badRequest().body("Book is already purchased");
				}
			}
			else if(reader2.isEmpty()) {
				String paymentid=purchaseBook(buybookrequest);
				return ResponseEntity.ok("Book Purchase Successful \n Please note the Payment Id for your reference\nPayment Id : "+paymentid);
			}
			else {
				return ResponseEntity.badRequest().body("User/Email has already exists,\n Please Try with different one's");
			}
		}
		return ResponseEntity.badRequest().body("Invalid Book Id / Book not available");
	}
	/**
	 * Finding All Purchased Books by Reader email
	 * @param emailid
	 * @return
	 */
	@GetMapping("/readers/{emailid}/books")
	ResponseEntity<?> getPurchasedBooks(@PathVariable("emailid") String emailid){
		List<Long> purchasedbookids = readerRepository.findAllByReaderemail(emailid).stream().map(Reader::getBookid).collect(Collectors.toList());
		if(!purchasedbookids.isEmpty()) {
			return ResponseEntity.ok(bookRepository.findAllById(purchasedbookids));
		}
		return ResponseEntity.badRequest().body("Invalid Email Id / No Books Purchased");
	}
	/**
	 * Reader Can Read books by Email Id and Book Id
	 * @param emailid
	 * @param bookid
	 * @return
	 */
	@GetMapping("/readers/{emailid}/books/{bookid}")
	ResponseEntity<?> getBookContent(@PathVariable("emailid") String emailid,@PathVariable("bookid") Long bookid){
		Optional<Reader> reader =readerRepository.findByReaderemailAndBookid(emailid, bookid);
		if (reader.isPresent()) {
			return  ResponseEntity.ok(bookRepository.findById(reader.get().getBookid()));
		}
		return ResponseEntity.badRequest().body("Invalid Email / Book");
	}
	/**
	 * Reader can read a book by Email and payment Id
	 * @param emailid
	 * @param pid
	 * @return
	 */
	@PostMapping("/readers/{emailid}/books")
	ResponseEntity<?> getBookByPaymentId(@PathVariable("emailid") String emailid,@RequestParam(required=true) String paymentid){
		Optional<Reader> reader =readerRepository.findByReaderemailAndPaymentid(emailid, paymentid);
		if (reader.isPresent()) {
			return  ResponseEntity.ok(bookRepository.findById(reader.get().getBookid()));
		}
		return ResponseEntity.badRequest().body("Invalid Email / Payment ID");
	}
	/**
	 * Reader can return / ask for a refund
	 * @param emailid
	 * @param bookid
	 * @return
	 */
	@PostMapping("/readers/{emailid}/books/{bookid}/refund")
	ResponseEntity<?> getRefundBookByBookId(@PathVariable("emailid") String emailid,@PathVariable("bookid") Long bookid){
		Optional<Reader> reader =readerRepository.findByReaderemailAndBookid(emailid,bookid);
		if (reader.isPresent()) {
			readerRepository.delete(reader.get());
			return ResponseEntity.ok("Book Unpurchased, refund will be credited shortly");
		}
		else {			
			return new ResponseEntity<>("Invalid Email Id / Book",HttpStatus.NOT_FOUND);
		}
	}
}
