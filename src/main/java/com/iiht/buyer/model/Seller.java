package com.iiht.buyer.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
	@NotNull(message = "FirstName cannot be null")
	@Size(min = 5, max = 30, message = "FirstName must be between 5 and 30 characters")
	private String firstName;

	@NotNull(message = "LastName cannot be null")
	@Size(min = 3, max = 25, message = "LastName must be between 5 and 30 characters")
	private String lastName;
	private String address;
	private String city;
	private String state;
	private int pin;

	@NotNull(message = "Phone cannot be null")
	@Size(min = 10, max = 10, message = "Phone number should contain 10 digits ")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number will allow only numbers")
	private String phone;

	@NotNull(message = "Email cannot be null")
	@Email(message = "Email should be valid")
	private String email;

}
