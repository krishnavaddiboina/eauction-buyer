package com.iiht.buyer.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.iiht.buyer.util.AppConstants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {	
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String status;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstants.RESPONSE_DATE_PATTERN)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Date responseTime;

}
