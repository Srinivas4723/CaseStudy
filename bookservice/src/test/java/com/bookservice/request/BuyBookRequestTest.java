package com.bookservice.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.bookservice.entity.Book;

class BuyBookRequestTest {
	private BuyBookRequest SampleBuyBookRequest1() {
		BuyBookRequest buyBookRequest = new BuyBookRequest();
		buyBookRequest.setBookid(Long.valueOf(1));
		buyBookRequest.setReadername("abc");
		buyBookRequest.setReaderemail("abc@gmail.com");
		return buyBookRequest;
	}
	private BuyBookRequest SampleBuyBookRequest2() {
		BuyBookRequest buyBookRequest = new BuyBookRequest();
		buyBookRequest.setBookid(Long.valueOf(2));
		buyBookRequest.setReadername("xyz");
		buyBookRequest.setReaderemail("xyz@yahoo.com");
		return buyBookRequest;
	}
	BuyBookRequest buyBookRequest1= SampleBuyBookRequest1();
	BuyBookRequest buyBookRequest2= SampleBuyBookRequest1();
	BuyBookRequest buyBookRequest3= SampleBuyBookRequest2();
	@Test
	void testHashCode() {
		assertEquals(new BuyBookRequest().hashCode(),new BuyBookRequest().hashCode());
		assertEquals(buyBookRequest1.hashCode(),buyBookRequest2.hashCode());
		assertNotEquals(buyBookRequest1.hashCode(),buyBookRequest3.hashCode());
	}

	@Test
	void testEqualsObject() {
		assertTrue(new BuyBookRequest().equals(new BuyBookRequest()));
		assertFalse(buyBookRequest1.equals(buyBookRequest3));
		assertTrue(buyBookRequest1.equals(buyBookRequest2));
	}

	@Test
	void testToString() {
		assertEquals(buyBookRequest1.toString(),buyBookRequest1.toString());
	}

}
