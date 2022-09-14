package com.bookservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(	name = "purchasedbooks", 
uniqueConstraints = { 
	@UniqueConstraint(columnNames = "id")
})
public class PurchasedBooks {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String readeremail;
	private Long bookid;
	private String paymentid;

}
