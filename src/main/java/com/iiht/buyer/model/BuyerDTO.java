package com.iiht.buyer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerDTO {

	private String id;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String pin;
	private String phone;
	private String email;
	private String productId;
	private String bidAmount;

}
