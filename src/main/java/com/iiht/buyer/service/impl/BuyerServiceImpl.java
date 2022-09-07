package com.iiht.buyer.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.iiht.buyer.exception.BidException;
import com.iiht.buyer.exception.InvalidDateException;
import com.iiht.buyer.model.Buyer;
import com.iiht.buyer.model.Product;
import com.iiht.buyer.repository.BuyerRepository;

import com.iiht.buyer.service.BuyerService;

@Service
public class BuyerServiceImpl implements BuyerService {	
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	BuyerRepository buyerRepository;

	@Override
	public void placeBidForProduct(Buyer buyer) throws InvalidDateException, BidException {

		Product product = buyerRepository.getProductDetails(buyer.getProductId());
		if (product != null) {
			System.out.println("Product exist in the db");
			if (product.getBidEndDate() != null) {
				String dateFormat = "yyyy-MM-dd";

				String bidEndDate = product.getBidEndDate().toInstant().atOffset(ZoneOffset.UTC)
						.format(DateTimeFormatter.ofPattern(dateFormat));

				LocalDate localDate = LocalDate.now(ZoneId.systemDefault());

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);
				LocalDate thebidEndDate = LocalDate.parse(bidEndDate, dtf);

				if (localDate.isAfter(thebidEndDate)) {
					throw new InvalidDateException("Bid end date expired");
				}
			}

			boolean flag = buyerRepository.isBidExistWithUser(buyer.getProductId(), buyer.getEmail());
			if(flag == true) {
				throw new BidException("Only one bid allowed for the product");
			}
			
			buyerRepository.placeBidForProduct(buyer);

		}

	}

	@Override
	public void updateBidForProduct(String productId, String buyerEmailId, String newBidAmount) throws InvalidDateException {
		Product product = buyerRepository.getProductDetails(productId);
		if (product.getBidEndDate() != null) {
			String dateFormat = "yyyy-MM-dd";

			String bidEndDate = product.getBidEndDate().toInstant().atOffset(ZoneOffset.UTC)
					.format(DateTimeFormatter.ofPattern(dateFormat));

			LocalDate localDate = LocalDate.now(ZoneId.systemDefault());

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);
			LocalDate thebidEndDate = LocalDate.parse(bidEndDate, dtf);

			if (localDate.isAfter(thebidEndDate)) {
				throw new InvalidDateException("Bid end date expired");
			}
		}
		
		buyerRepository.updateBidData(productId, buyerEmailId, newBidAmount);
		
	}

}
