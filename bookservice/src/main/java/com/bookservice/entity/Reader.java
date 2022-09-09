package com.bookservice.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
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
	public Reader(String readerName2, String readerEmail2) {
		// TODO Auto-generated constructor stub
	}
	
	
	 
	
}
