package com.bookservice.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(	name = "readers", 
uniqueConstraints = { 
	@UniqueConstraint(columnNames = "id")
})
public class Reader {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "readername cannot be blank#######")
	private String readername;
	@NotBlank(message = "readeremail cannot be blank#######")
	private String readeremail;
	 private String books;
	 private String paymentid;
	 public Reader() {
		 
	 }
	public String getPaymentid() {
		return paymentid;
	}
	public Reader(String readername, String readeremail) {
		this.readername=readername;
		this.readeremail=readeremail;
	}
	public String getBooks() {
		return books;
	}
	public void setBooks(String books) {
		this.books = books;
	}
	public void setPaymentid(String paymentid) {
		this.paymentid = paymentid;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReadername() {
		return readername;
	}
	public void setReadername(String readername) {
		this.readername = readername;
	}
	public String getReaderemail() {
		return readeremail;
	}
	public void setReaderemail(String readeremail) {
		this.readeremail = readeremail;
	}
	
	
	
	 
	
}
