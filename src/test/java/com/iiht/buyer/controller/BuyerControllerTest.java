package com.iiht.buyer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.PathVariable;

import com.iiht.buyer.BuyerApplication;
import com.iiht.buyer.exception.BiddingException;
import com.iiht.buyer.exception.InvalidInputException;
import com.iiht.buyer.exception.MongoDBException;
import com.iiht.buyer.model.Buyer;
import com.iiht.buyer.model.BuyerDTO;
import com.iiht.buyer.model.ProductResponse;
import com.iiht.buyer.service.BuyerService;

@DataMongoTest
@ContextConfiguration(classes = { BuyerApplication.class })
@EnableAutoConfiguration(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
class BuyerControllerTest {
	
	@Mock
	BuyerService buyerService;
	
	@InjectMocks
	BuyerController buyerController;

	@Test
	void testPlaceBidOnProduct() throws InvalidInputException, BiddingException, MongoDBException {
		String buyerId = "123";
		BuyerDTO buyerDto = new BuyerDTO();
		buyerDto.setFirstName("firstName");
		Buyer buyer = new Buyer();
		BeanUtils.copyProperties(buyerDto, buyer);
		ProductResponse productResponse = new ProductResponse();
		productResponse.setStatus("201");
		
		Mockito.when(buyerService.placeBidForProduct(Mockito.any(), Mockito.any())).thenReturn(buyerId);
		assertEquals(productResponse.getStatus(), buyerController.placeBidForProduct(buyerDto).getBody().getStatus());
		
	}
	
	@Test
	void testUpdateBidForProduct() throws InvalidInputException, MongoDBException {
		String productId = "123";
		String buyerEmailId = "test@gmail.com";
	    String newBidAmount = "100";
		ProductResponse response = new ProductResponse();
		response.setStatus("200");
		Mockito.when(buyerService.updateBidForProduct(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
		assertEquals(response.getStatus(), buyerController.updateBidForProduct(productId, buyerEmailId, newBidAmount).getBody().getStatus());
	}

}
