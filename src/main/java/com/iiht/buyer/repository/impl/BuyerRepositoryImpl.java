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
import com.mongodb.client.result.UpdateResult;

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
		} catch (Exception exception) {
			log.error("Error occured while getting product details by product id. Error is {}", exception.getMessage());
			throw new MongoDBException("Error occured while getting product details by product id in mongo db");
		}
		return product;
	}

	@Override
	public boolean isBidExistWithUser(String productId, String buyerEmail) throws MongoDBException {
		boolean flag = false;
		try {
			Query query = new Query(Criteria.where("productId").is(productId));
			List<Buyer> buyers = mongoTemplate.find(query, Buyer.class);
			if (buyers != null && buyers.size() > 0) {
				long count = buyers.stream().filter(buyer -> buyer.getEmail().equalsIgnoreCase(buyerEmail)).count();
				if (count == 1) {
					flag = true;
					return flag;
				}
			}
		} catch (Exception exception) {
			log.error("Error occured while checking bid exist with user. Error is {}", exception.getMessage());
			throw new MongoDBException("Error occured while checking bid exist with user in mongo db");
		}
		return flag;

	}

	@Override
	public boolean updateBidData(String productId, String buyerEmailId, String newBidAmount) throws MongoDBException {
		log.debug("Within updateBidData() of BuyerRepositoryImpl class...");
		UpdateResult updateResult = null;
		try {
			 updateResult = mongoTemplate.updateFirst(
					Query.query(Criteria.where("productId").is(productId).and("email").is(buyerEmailId)),
					Update.update("bidAmount", newBidAmount), Buyer.class);
			log.info("acknowledged value is {}",updateResult.wasAcknowledged());
		} catch (Exception exception) {
			log.error("Error occured while updating bid amount. Error is {}", exception.getMessage());
			throw new MongoDBException("Error occured while updating bid amount in mongo db");
		}
		return updateResult.wasAcknowledged();
	}

	@Override
	public String placeBidForProduct(Buyer buyer) throws MongoDBException {
		log.debug("Within placeBidForProduct() of BuyerRepositoryImpl class...");
		String buyerId = null;
		try {
			Buyer theBuyer = mongoTemplate.save(buyer);
			if (theBuyer != null) {
				return theBuyer.getId();
			}
		} catch (Exception exception) {
			log.error("Error occured while placing bid for the product. Error is {}", exception.getMessage());
			throw new MongoDBException("Error occured while placing bid for the product in mongo db");
		}
		return buyerId;
	}

}
