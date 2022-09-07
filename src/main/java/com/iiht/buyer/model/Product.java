package com.iiht.buyer.model;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("product")
public class Product {

	@Id
	private String id;

	@NotNull(message = "ProductName cannot be null")
	@Size(min = 5, max = 30, message = "ProductName must be between 5 and 30 characters")
	private String productName;
	private String shortDescription;
	private String detailedDescription;
	@Pattern(regexp = "Painting|Sculptor|Ornament", message = "Category should be anyone of the following. Painting|Sculptor|Ornament")
	private String category;
	@Pattern(regexp = "^[0-9]+$", message = "the value must be number")
	private String startingPrice;
	private Date bidEndDate;

	@NotNull
	@Valid
	private Seller seller;

}
