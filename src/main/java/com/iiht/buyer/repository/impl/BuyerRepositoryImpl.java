package com.iiht.buyer.repository.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.iiht.buyer.model.Buyer;
import com.iiht.buyer.model.Product;
import com.iiht.buyer.repository.BuyerRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Repository
public class BuyerRepositoryImpl implements BuyerRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Product getProductDetails(String productId) {
		Query query = new Query(Criteria.where("id").is(new ObjectId(productId)));
		List<Product> products = mongoTemplate.find(query, Product.class);
		if (products != null && products.size() > 0) {
			return products.get(0);
		} else {
			return null;
		}
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
