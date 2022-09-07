package com.iiht.buyer.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.iiht.buyer.exception.BiddingException;
import com.iiht.buyer.exception.DeleteBidException;
import com.iiht.buyer.exception.InvalidInputException;
import com.iiht.buyer.model.Buyer;
import com.iiht.buyer.model.Product;
import com.iiht.buyer.model.ProductResponse;
import com.iiht.buyer.repository.BuyerRepository;
import com.iiht.buyer.service.BuyerService;
import com.iiht.buyer.validator.BuyerDataValidator;
import com.iiht.buyer.util.AppConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BuyerServiceImpl implements BuyerService {

	

	@Autowired
	BuyerRepository buyerRepository;
	
	@Autowired
	BuyerDataValidator validator;

	@Override
	public void placeBidForProduct(Buyer buyer, ProductResponse response)
			throws InvalidInputException, BiddingException {

		log.debug("Within placeBidForProduct() of BuyerServiceImpl class...");
		validator.validateRequestData(buyer, response);
		log.info("request data validation successfull...");
		
		Product product = buyerRepository.getProductDetails(buyer.getProductId());
		if (product != null && product.getBidEndDate() != null) {
			LocalDate bidEnddate = getFormattedBidEndDate(product.getBidEndDate());
			LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());

			if (currentDate.isAfter(bidEnddate)) {
				log.error("Bid end date expired");
				throw new InvalidInputException("Bid end date expired", response);
			}
		}

			boolean flag = buyerRepository.isBidExistWithUser(buyer.getProductId(), buyer.getEmail());
			if (flag == true) {
				log.error("Only one bid allowed for the product");
				throw new BiddingException("Only one bid allowed for the product", response);
			}

			buyerRepository.placeBidForProduct(buyer);

		

	}

	@Override
	public void updateBidForProduct(String productId, String buyerEmailId, String newBidAmount)
			throws InvalidDateException {
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
	
	public LocalDate getFormattedBidEndDate(Date bidEndDate) {
		String theBidEndDate = bidEndDate.toInstant().atOffset(ZoneOffset.UTC)
				.format(DateTimeFormatter.ofPattern(AppConstants.DATE_FORMAT));

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(AppConstants.DATE_FORMAT);
		return LocalDate.parse(theBidEndDate, dtf);
	}

}
