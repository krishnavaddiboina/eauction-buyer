package com.iiht.buyer.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.buyer.exception.BiddingException;
import com.iiht.buyer.exception.InvalidInputException;
import com.iiht.buyer.exception.MongoDBException;
import com.iiht.buyer.model.Buyer;
import com.iiht.buyer.model.Product;
import com.iiht.buyer.model.ProductResponse;
import com.iiht.buyer.repository.BuyerRepository;
import com.iiht.buyer.service.BuyerService;
import com.iiht.buyer.util.AppConstants;
import com.iiht.buyer.validator.BuyerDataValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BuyerServiceImpl implements BuyerService {

	

	@Autowired
	BuyerRepository buyerRepository;
	
	@Autowired
	BuyerDataValidator validator;

	@Override
	public String placeBidForProduct(Buyer buyer, ProductResponse response)
			throws InvalidInputException, BiddingException, MongoDBException {

		log.debug("Within placeBidForProduct() of BuyerServiceImpl class...");
		validator.validateRequestData(buyer, response);
		log.info("request data validation successfull...");
		
		Product product = buyerRepository.getProductDetails(buyer.getProductId());
		if (product != null && product.getBidEndDate() != null) {
			LocalDate bidEnddate = getFormattedBidEndDate(product.getBidEndDate());
			LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());

			if (currentDate.isAfter(bidEnddate)) {
				log.error("Bid end date expired....");
				throw new InvalidInputException("Bid end date expired", response);
			}
			
			boolean flag = buyerRepository.isBidExistWithUser(buyer.getProductId(), buyer.getEmail());
			if (flag) {
				log.error("Only one bid allowed for the product");
				throw new BiddingException("Only one bid allowed for the product", response);
			}
			log.info("Going to place the bid...");
			return buyerRepository.placeBidForProduct(buyer);
		}else {
			log.error("Product Id does not exist....");
			throw new InvalidInputException("Product Id does not exist", response);
		}

			

		

	}

	@Override
	public boolean updateBidForProduct(String productId, String buyerEmailId, String newBidAmount, ProductResponse response)
			throws InvalidInputException, MongoDBException {
		log.debug("Within updateBidForProduct() of BuyerServiceImpl class....");
		Product product = buyerRepository.getProductDetails(productId);
		if (product != null && product.getBidEndDate() != null) {
			LocalDate bidEnddate = getFormattedBidEndDate(product.getBidEndDate());
			LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());

			if (currentDate.isAfter(bidEnddate)) {
				log.error("Bid end date expired....");
				throw new InvalidInputException("Bid end date expired", response);
			}
			
			return buyerRepository.updateBidData(productId, buyerEmailId, newBidAmount);
		}else {
			log.error("Product Id does not exist....");
			throw new InvalidInputException("Product Id does not exist", response);
		}

		

	}
	
	public LocalDate getFormattedBidEndDate(Date bidEndDate) {
		String theBidEndDate = bidEndDate.toInstant().atOffset(ZoneOffset.UTC)
				.format(DateTimeFormatter.ofPattern(AppConstants.DATE_FORMAT));

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(AppConstants.DATE_FORMAT);
		return LocalDate.parse(theBidEndDate, dtf);
	}

}
