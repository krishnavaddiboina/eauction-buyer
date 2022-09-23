package com.iiht.buyer.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;

import com.iiht.buyer.exception.MongoDBException;
import com.iiht.buyer.model.Product;
import com.iiht.buyer.repository.BuyerRepository;
import com.mongodb.client.result.UpdateResult;
import com.iiht.buyer.model.Buyer;

@DataMongoTest
@ContextConfiguration(classes = { BuyerRepository.class })
@EnableAutoConfiguration(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
class BuyerRepositoryImplTest {
	
	@Mock
	MongoTemplate mongoTemplate;
	
	@InjectMocks
	BuyerRepositoryImpl buyerRepositoryImpl;

	@Test
	void testGetProductDetails() throws MongoDBException {
		String productId = "123";
		Product product = new Product();		
		product.setProductName("productName");
		
		Mockito.when(mongoTemplate.findById(Mockito.any(), Mockito.any())).thenReturn(product);
		assertEquals(product, buyerRepositoryImpl.getProductDetails(productId));
	}
	
	@Test
	void testIsBidExistWithUser() throws MongoDBException {
		String productId = "123";
		String email = "test@gmail.com";
		List<Object> buyers = new ArrayList<>();
		Buyer buyer = new Buyer();
		buyer.setFirstName("buyerName");
		buyer.setEmail("test@gmail.com");
		buyers.add(buyer);		
		
		Mockito.when(mongoTemplate.find(Mockito.any(), Mockito.any())).thenReturn(buyers);
		assertEquals(true, buyerRepositoryImpl.isBidExistWithUser(productId, email));
	}	
	
	
	@Test
	void testPlaceBidForProduct() throws MongoDBException {
		Buyer buyer = new Buyer();
		buyer.setId("id");
		buyer.setFirstName("buyerName");
		buyer.setEmail("test@gmail.com");
		
		Mockito.when(mongoTemplate.save(Mockito.any())).thenReturn(buyer);
		assertEquals(buyer.getId(), buyerRepositoryImpl.placeBidForProduct(buyer));
	}

}
