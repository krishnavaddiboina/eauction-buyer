package com.iiht.buyer.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;

import com.iiht.buyer.exception.BiddingException;
import com.iiht.buyer.exception.InvalidInputException;
import com.iiht.buyer.exception.MongoDBException;
import com.iiht.buyer.model.Buyer;
import com.iiht.buyer.model.Product;
import com.iiht.buyer.model.ProductResponse;
import com.iiht.buyer.repository.BuyerRepository;
import com.iiht.buyer.service.BuyerService;
import com.iiht.buyer.validator.BuyerDataValidator;

@DataMongoTest
@ContextConfiguration(classes = { BuyerService.class })
@EnableAutoConfiguration(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
class BuyerServiceImplTest {
	
	@Mock
	BuyerRepository buyerRepository;
	
	@Mock
	BuyerDataValidator validator;
	
	@InjectMocks
	BuyerServiceImpl buyerServiceImpl;
	

	@Test
	void testPlaceBidForProduct() throws InvalidInputException, MongoDBException, BiddingException {
		String buyerId = "123";
		Buyer buyer = new Buyer();
		buyer.setFirstName("firstName");
		buyer.setProductId("productId");
		ProductResponse response = new ProductResponse();
		
		Product product = new Product();		
		product.setProductName("productName");
		
		Date date = new Date();	   
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    c.add(Calendar.DATE, 1); //adding 1day extra to the current date
	    date = c.getTime();
		product.setBidEndDate(date);		
		
		Mockito.doNothing().when(validator).validateRequestData(Mockito.any(), Mockito.any());		
		Mockito.when(buyerRepository.getProductDetails(Mockito.any())).thenReturn(product);
		Mockito.when(buyerRepository.placeBidForProduct(Mockito.any())).thenReturn(buyerId);
		
		assertEquals(buyerId, buyerServiceImpl.placeBidForProduct(buyer, response));
	}
	
	@Test
	void testUpdateBidForProduct() throws MongoDBException, InvalidInputException {
		String productId = "123";
		String buyerEmailId = "test@gmail.com";
		String newBidAmount = "100";
		Product product = new Product();		
		product.setProductName("productName");
		
		Date date = new Date();	   
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    c.add(Calendar.DATE, 1); //adding 1day extra to the current date
	    date = c.getTime();
		product.setBidEndDate(date);	
		ProductResponse productResponse = new ProductResponse();
			
		Mockito.when(buyerRepository.getProductDetails(Mockito.any())).thenReturn(product);
		Mockito.when(buyerRepository.updateBidData(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
		assertTrue(buyerServiceImpl.updateBidForProduct(productId, buyerEmailId, newBidAmount, productResponse));
	}
	
	void testUpdateBidForProduct_ExceptionCase() throws MongoDBException, InvalidInputException {
		String productId = "123";
		String buyerEmailId = "test@gmail.com";
		String newBidAmount = "100";
		Product product = new Product();		
		product.setProductName("productName");
		
		Date date = new Date();	   
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    c.add(Calendar.DATE, -1); //adding 1day extra to the current date
	    date = c.getTime();
		product.setBidEndDate(date);	
		ProductResponse productResponse = new ProductResponse();
			
		Mockito.when(buyerRepository.getProductDetails(Mockito.any())).thenReturn(product);
		Mockito.when(buyerRepository.updateBidData(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
		assertEquals("Bid end date expired",buyerServiceImpl.updateBidForProduct(productId, buyerEmailId, newBidAmount, productResponse));
	}

}
