package com.iiht.buyer.service;

import com.iiht.buyer.exception.BiddingException;
import com.iiht.buyer.exception.InvalidInputException;
import com.iiht.buyer.model.Buyer;
import com.iiht.buyer.model.ProductResponse;

public interface BuyerService {

	void placeBidForProduct(Buyer buyer, ProductResponse response) throws InvalidInputException, BiddingException;

	void updateBidForProduct(String productId, String buyerEmailId, String newBidAmount) throws InvalidInputException;

}
