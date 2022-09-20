package com.iiht.buyer.controller;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iiht.buyer.exception.BiddingException;
import com.iiht.buyer.exception.InvalidInputException;
import com.iiht.buyer.exception.MongoDBException;
import com.iiht.buyer.model.Buyer;
import com.iiht.buyer.model.BuyerDTO;
import com.iiht.buyer.model.ProductResponse;
import com.iiht.buyer.service.BuyerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BuyerController {

	@Autowired
	BuyerService buyerService;

	@PostMapping("/place-bid")
	public ResponseEntity<ProductResponse> placeBidForProduct(@RequestBody BuyerDTO buyerDto)
			throws InvalidInputException, BiddingException, MongoDBException {	

		log.info("Received buyer in the controller...buyer is {}", buyerDto);
		ProductResponse productResponse = new ProductResponse();

		Buyer buyer = new Buyer();
		BeanUtils.copyProperties(buyerDto, buyer);
		String buyerId = buyerService.placeBidForProduct(buyer, productResponse);
		log.info("After adding the buyer, buyer id is {} ", buyerId);
		productResponse.setResponseTime(new Date());
		if (buyerId != null) {
			productResponse.setMessage("Bid placed successfully");
			productResponse.setStatus(String.valueOf(HttpStatus.CREATED.value()));
			return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
		} else {
			log.info("Bid placing failure...");
			productResponse.setMessage("Not able to place bid");
			productResponse.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			return new ResponseEntity<>(productResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/update-bid/{productId}/{buyerEmailId}/{newBidAmount}")
	public ResponseEntity<ProductResponse> updateBidForProduct(@PathVariable String productId,
			@PathVariable String buyerEmailId, @PathVariable String newBidAmount)
			throws InvalidInputException, MongoDBException {

		log.info("Within updateBidForProduct()...productId {}, buyerEmailId {}, newBidAmount {}", productId,
				buyerEmailId, newBidAmount);
		ProductResponse response = new ProductResponse();

		boolean flag = buyerService.updateBidForProduct(productId, buyerEmailId, newBidAmount, response);
		if(flag) {
			log.info("Bid updated successfully.");
			response.setMessage("Bid updated successfully");
			response.setStatus(String.valueOf(HttpStatus.OK.value()));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}else {
			log.info("Not able to update bid amount.");
			response.setMessage("Not able to update bid amount.");
			response.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
