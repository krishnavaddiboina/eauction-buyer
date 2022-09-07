package com.iiht.buyer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iiht.buyer.model.Buyer;
import com.iiht.buyer.model.ProductResponse;
import com.iiht.buyer.service.BuyerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BuyerController {

	@Autowired
	BuyerService buyerService;

	@PostMapping("/place-bid")
	public ResponseEntity<ProductResponse> placeBidForProduct(@RequestBody Buyer buyer) {
		ProductResponse response = new ProductResponse();
		buyerService.placeBidForProduct(buyer, response);
		
		return new ResponseEntity<String>("Bid placed successfully", HttpStatus.CREATED);

	}

	@PostMapping("/update-bid/{productId}/{buyerEmailId}/{newBidAmount}")
	public ResponseEntity<String> updateBidForProduct(@PathVariable String productId, @PathVariable String buyerEmailId,
			@PathVariable String newBidAmount) {
		try {
			buyerService.updateBidForProduct(productId, buyerEmailId, newBidAmount);
			System.out.println("Bid updated successfully");
			return new ResponseEntity<String>("Bid updated successfully", HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Error while updating bid");
			return new ResponseEntity<String>("Error while updating bid", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
