package com.iiht.buyer.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import com.iiht.buyer.model.Buyer;
import com.iiht.buyer.exception.InvalidInputException;
import com.iiht.buyer.model.ProductResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BuyerDataValidator {
	
	@Autowired
	private Validator validator;
	
	public void validateRequestData(Buyer buyer, ProductResponse response) throws InvalidInputException {
		log.info("Within validateRequestData() of DataValidator....");
		DataBinder dataBinder = new DataBinder(buyer);
		dataBinder.setValidator(validator);
		dataBinder.validate();
		
		BindingResult bindingResult = dataBinder.getBindingResult();
		if(bindingResult.hasErrors()) {
			FieldError error = bindingResult.getFieldError();
			String exceptionMessage = error != null?error.getDefaultMessage():null;
			throw new InvalidInputException(exceptionMessage, response);
		}
	}

}
