package com.bookservice.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.bookservice.entity.Book;

class LoginRequestTest {
	public static LoginRequest SampleLoginRequest1() {
		LoginRequest loginrequest = new LoginRequest();
		loginrequest.setAuthorname("abc");
		loginrequest.setPassword("abc");
		return loginrequest;
	}
	public static LoginRequest SampleLoginRequest2() {
		LoginRequest loginrequest = new LoginRequest();
		loginrequest.setAuthorname("abc1");
		loginrequest.setPassword("abc1");
		return loginrequest;
	}
	LoginRequest loginRequest1= SampleLoginRequest1();
	LoginRequest loginRequest2= SampleLoginRequest1();
	LoginRequest loginRequest3= SampleLoginRequest2();
	@Test
	void testHashCode() {
		assertEquals(new LoginRequest().hashCode(),new LoginRequest().hashCode());
		assertEquals(loginRequest1.hashCode(),loginRequest2.hashCode());
		assertNotEquals(loginRequest1.hashCode(),loginRequest3.hashCode());
	}

	@Test
	void testEqualsObject() {
		assertTrue(new LoginRequest().equals(new LoginRequest()));
		assertFalse(loginRequest1.equals(loginRequest3));
		assertTrue(loginRequest1.equals(loginRequest2));
		assertFalse(loginRequest1.equals(null));
	}

	@Test
	void testToString() {
		assertEquals(loginRequest1.toString(),loginRequest1.toString());
	}

}
