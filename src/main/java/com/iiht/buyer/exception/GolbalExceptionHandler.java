package com.iiht.buyer.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.iiht.buyer.model.ProductResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GolbalExceptionHandler {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<ProductResponse> handleInvalidInputException(InvalidInputException exception){
		log.error("Withing handleInvalidInputException() of GolbalExceptionHandler. Error is {}", exception.getMessage());
		ProductResponse productResponse = exception.getProductResponse();
		productResponse.setResponseTime(new Date());
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(MongoDBException.class)
	public ResponseEntity<ProductResponse> INTERNAL_SERVER_ERROR(MongoDBException exception){
		log.error("Withing handleMongoDBException() of GolbalExceptionHandler. Error is {}", exception.getMessage());
		ProductResponse productResponse = exception.getProductResponse();
		productResponse.setResponseTime(new Date());
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BiddingException.class)
	public ResponseEntity<ProductResponse> handleDeleteBidException(BiddingException exception){
		log.error("Withing handleDeleteBidException() of GolbalExceptionHandler. Error is {}", exception.getMessage());
		ProductResponse productResponse = exception.getProductResponse();
		productResponse.setResponseTime(new Date());
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.BAD_REQUEST);
	}
	
	
	

}
