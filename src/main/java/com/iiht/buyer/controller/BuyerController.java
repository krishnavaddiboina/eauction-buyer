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
import com.iiht.buyer.service.BuyerService;

@RestController
public class BuyerController {

	@Autowired
	BuyerService buyerService;

	@PostMapping("/place-bid")
	public ResponseEntity<String> placeBidForProduct(@RequestBody Buyer buyer) {
	
			buyerService.placeBidForProduct(buyer);
			System.out.println("Bid placed successfully");
			return new ResponseEntity<String>("Bid placed successfully", HttpStatus.CREATED);
		

	}
	
	@PostMapping("/update-bid/{productId}/{buyerEmailId}/{newBidAmount}")
	public ResponseEntity<String> updateBidForProduct(@PathVariable String productId, @PathVariable String buyerEmailId, @PathVariable String newBidAmount) {
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
