package com.iiht.buyer.repository;

import com.iiht.buyer.model.Buyer;
import com.iiht.buyer.model.Product;

public interface BuyerRepository {
	
	Product getProductDetails(String productId);

	boolean isBidExistWithUser(String productId, String buyerEmail);

	void updateBidData(String productId, String buyerEmailId, String newBidAmount);

	Buyer placeBidForProduct(Buyer buyer);
}
