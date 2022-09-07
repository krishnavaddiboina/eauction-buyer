package com.iiht.buyer.exception;

import org.springframework.http.HttpStatus;

import com.iiht.buyer.model.ProductResponse;

public class MongoDBException extends Exception {

	private static final long serialVersionUID = -6029432326676393698L;
	private String message;
	private ProductResponse productResponse;

	public MongoDBException() {
		super();
	}

	public MongoDBException(String message) {
		super(message);
		this.message = message;		
		productResponse.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		productResponse.setMessage(message);		
	}

	public String getMessage() {
		return message;
	}

	public ProductResponse getProductResponse() {
		return productResponse;
	}
	
	
}
