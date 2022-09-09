package com.bookservice.request;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Data;
@Data
public class BuyBookRequest{
	public BuyBookRequest(Long valueOf, String string, String string2) {
		// TODO Auto-generated constructor stub
	}
	@Id
	private Long bookId;
	@NotBlank(message = "readername cannot be blank#######")
	private String readerName;
	@NotBlank(message = "Reader Email canno t be blank#######")
	private String readerEmail;
	private String paymentId;
	private int id;
}
