package com.bookservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(	name = "readers", 
uniqueConstraints = { 
	@UniqueConstraint(columnNames = "id"),
	@UniqueConstraint(columnNames = "readername"),
	@UniqueConstraint(columnNames = "readeremail")
})
public class Reader {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "readername cannot be blank#######")
	private String readername;
	@NotBlank(message = "readeremail cannot be blank#######")
	private String readeremail;
	
}
