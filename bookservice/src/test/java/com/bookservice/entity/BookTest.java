package com.bookservice.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class BookTest {
	@InjectMocks Book injectBook;
	private  Book SampleBook1() {
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
		return book;
	}
	private  Book SampleBook2() {
		Book book = new Book();
		book.setId(Long.valueOf(2));
		book.setAuthorid(Long.valueOf(1));
		book.setTitle("title1");
		book.setCategory(Category.FANTASY);
		book.setPrice(BigDecimal.valueOf(600.00));
		book.setAuthor("author1");
		book.setPublisher("publisher1");
		book.setPublisheddate(LocalDate.of(1987,Month.SEPTEMBER,15));
		book.setChapters(15);
		book.setActive(false);
		return book;
	}
	Book book1= SampleBook1();
	Book book2= SampleBook1();
	Book book3=SampleBook2();
	@Test
	void testHashCode() {
		assertEquals(new Book().hashCode(),new Book().hashCode());
		assertEquals(book1.hashCode(),book2.hashCode());
		assertNotEquals(book1.hashCode(),book3.hashCode());
	}

	@Test
	void testEqualsObject() {
		assertTrue(new Book().equals(new Book()));
		assertFalse(book1.equals(book3));
		assertTrue(book1.equals(book2));
		
	}

	@Test
	void testToString() {
		assertEquals(book1.toString(),book1.toString());
	}

}
