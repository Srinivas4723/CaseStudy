package com.bookservice.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class AuthorTest {
	public static Author SampleAuthor1() {
		Author author = new Author();
		author.setId(Long.valueOf(1));
		author.setAuthorname("abc");
		author.setAuthoremail("abc@gmail.com");
		author.setLoginstatus(false);
		author.setPassword("abc");
		return author;
	}
	public static Author SampleAuthor2() {
		Author author = new Author();
		author.setId(Long.valueOf(2));
		author.setAuthorname("abc1");
		author.setAuthoremail("abc1@gmail.com");
		author.setLoginstatus(true);
		author.setPassword("abc1");
		return author;
	}
	Author author1= SampleAuthor1();
	Author author2= SampleAuthor1();
	Author author3= SampleAuthor2();
	@Test
	void testHashCode() {	
		assertEquals(new Author().hashCode(),new Author().hashCode());
		assertEquals(author1.hashCode(),author2.hashCode());
		assertNotEquals(author1.hashCode(),author3.hashCode());
	}

	@Test
	void testEqualsObject() {
		assertTrue(new Author().equals(new Author()));
		assertFalse(author1.equals(author3));
		assertTrue(author1.equals(author2));
		assertFalse(author2.equals(null));
	}

	

	@Test
	void testToString() {
		assertEquals(author1.toString(),author1.toString());
	}

	

}
