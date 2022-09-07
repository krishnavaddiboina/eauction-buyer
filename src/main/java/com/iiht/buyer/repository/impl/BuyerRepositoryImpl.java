package com.iiht.buyer.repository.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.iiht.buyer.exception.MongoDBException;
import com.iiht.buyer.model.Buyer;
import com.iiht.buyer.model.Product;
import com.iiht.buyer.repository.BuyerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BuyerRepositoryImpl implements BuyerRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Product getProductDetails(String productId) throws MongoDBException {
		log.debug("Within getProductDetails() of BuyerRepositoryImpl class...");
		Product product = null;
		try {
			product = mongoTemplate.findById(productId, Product.class);
			log.info("Based on the given product id got the product {}", product);
		}catch(Exception exception) {
			log.error("Error occured while getting product details by product id. Error is {}", exception.getMessage());
			throw new MongoDBException("Error occured while getting product details by product id in mongo db");
		}
		return product;
	}

	@Override
	public boolean isBidExistWithUser(String productId, String buyerEmail) {
		boolean flag = false;
		Query query = new Query(Criteria.where("id").is(new ObjectId(productId)));
		List<Buyer> buyers = mongoTemplate.find(query, Buyer.class);
		if (buyers != null && buyers.size() > 0) {
			long count = buyers.stream().filter(buyer -> buyer.getEmail().equalsIgnoreCase(buyerEmail)).count();
			if (count == 0) {
				flag = true;
				return flag;
			}
		}
		return flag;

	}

	@Override
	public void updateBidData(String productId, String buyerEmailId, String newBidAmount) {

		Criteria criteria = new Criteria();
		criteria.and("productId").is(productId);
		criteria.and("buyerEmailId").is(buyerEmailId);

		Query query = new Query(criteria);

		// Query greatCmtsRemaining =
		// Query.query(Criteria.where("message").is("Great."));
		mongoTemplate.updateFirst(query, Update.update("bidAmount", newBidAmount), Buyer.class);

	}

	@Override
	public Buyer placeBidForProduct(Buyer buyer) {

		return mongoTemplate.save(buyer);
	}

}
