package com.iiht.buyer.validator;

package com.iiht.seller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import com.iiht.seller.exception.InvalidInputException;
import com.iiht.seller.model.Product;
import com.iiht.seller.model.ProductResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BuyerDataValidator {
	
	@Autowired
	private Validator validator;
	
	public void validateRequestData(Product product, ProductResponse response) throws InvalidInputException {
		log.info("Within validateRequestData() of DataValidator....");
		DataBinder dataBinder = new DataBinder(product);
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
