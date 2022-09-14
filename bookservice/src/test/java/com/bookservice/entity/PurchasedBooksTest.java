package com.bookservice.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PurchasedBooksTest {
	
	private PurchasedBooks SamplePurchasedBooks1() {
		PurchasedBooks purchasedBook = new PurchasedBooks();
		purchasedBook.setBookid(Long.valueOf(1));
		purchasedBook.setReaderemail("abc@gmail.com");
		purchasedBook.setPaymentid("PID1234");
		return purchasedBook;
	}
	private PurchasedBooks SamplePurchasedBooks2() {
		PurchasedBooks purchasedBook = new PurchasedBooks();
		purchasedBook.setBookid(Long.valueOf(2));
		purchasedBook.setReaderemail("abc1@gmail.com");
		purchasedBook.setPaymentid("PID12345");
		return purchasedBook;
	}
	PurchasedBooks purchasedBook1=SamplePurchasedBooks1();
	PurchasedBooks purchasedBook2=SamplePurchasedBooks1();
	PurchasedBooks purchasedBook3=SamplePurchasedBooks2();
	@Test
	void testHashCode() {	
		assertEquals(new Author().hashCode(),new Author().hashCode());
		assertEquals(purchasedBook1.hashCode(),purchasedBook2.hashCode());
		assertNotEquals(purchasedBook1.hashCode(),purchasedBook3.hashCode());
	}

	@Test
	void testEqualsObject() {
		assertTrue(new Author().equals(new Author()));
		assertFalse(purchasedBook1.equals(purchasedBook3));
		assertTrue(purchasedBook1.equals(purchasedBook2));
		
	}

	

	@Test
	void testToString() {
		assertEquals(purchasedBook1.toString(),purchasedBook1.toString());
	}

}
