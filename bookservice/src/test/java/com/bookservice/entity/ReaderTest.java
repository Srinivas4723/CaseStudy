package com.bookservice.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ReaderTest {
	private Reader SampleReader1() {
		Reader reader = new Reader();
		reader.setId(Long.valueOf(1));
		reader.setReadername("abc");
		reader.setReaderemail("abc@gmail.com");
		reader.setPurchasedbooks("");
		reader.setPaymentid("PID1");
		return reader;
	}
	private Reader SampleReader2() {
		Reader reader = new Reader();
		reader.setId(Long.valueOf(2));
		reader.setReadername("abc1");
		reader.setReaderemail("abc1@gmail.com");
		reader.setPurchasedbooks("1");
		reader.setPaymentid("PID2");
		return reader;
	}
	Reader reader1 =SampleReader1();
	Reader reader2= SampleReader1();
	Reader reader3= SampleReader2();
	@Test
	void testHashCode() {
		assertEquals(new Reader().hashCode(),new Reader().hashCode());
		assertEquals(reader1.hashCode(),reader2.hashCode());
		assertNotEquals(reader1.hashCode(),reader3.hashCode());
	}

	@Test
	void testEqualsObject() {
		assertTrue(new Reader().equals(new Reader()));
		assertFalse(reader1.equals(reader3));
		assertTrue(reader1.equals(reader2));
	}

	@Test
	void testToString() {
		assertEquals(reader1.toString(),reader2.toString());
		assertNotEquals(reader1.toString(),reader3.toString());
	}

}
