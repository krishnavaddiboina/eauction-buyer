package com.iiht.buyer.service;

import com.iiht.buyer.exception.BidException;
import com.iiht.buyer.exception.InvalidDateException;
import com.iiht.buyer.model.Buyer;

public interface BuyerService {

	void placeBidForProduct(Buyer buyer) throws InvalidDateException, BidException;

	void updateBidForProduct(String productId, String buyerEmailId, String newBidAmount) throws InvalidDateException;

}
