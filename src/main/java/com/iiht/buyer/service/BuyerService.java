package com.iiht.buyer.service;

import com.iiht.buyer.exception.BiddingException;
import com.iiht.buyer.exception.InvalidInputException;
import com.iiht.buyer.exception.MongoDBException;
import com.iiht.buyer.model.Buyer;
import com.iiht.buyer.model.ProductResponse;

public interface BuyerService {

	String placeBidForProduct(Buyer buyer, ProductResponse response) throws InvalidInputException, BiddingException, MongoDBException;

	boolean updateBidForProduct(String productId, String buyerEmailId, String newBidAmount, ProductResponse response) throws InvalidInputException, MongoDBException;

}
