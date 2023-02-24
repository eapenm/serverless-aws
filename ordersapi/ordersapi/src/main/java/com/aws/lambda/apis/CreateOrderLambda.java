package com.aws.lambda.apis;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.lambda.apis.modal.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateOrderLambda {
	
	public APIGatewayProxyResponseEvent createOrder(APIGatewayProxyRequestEvent request) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper =	new ObjectMapper();
		Order order = objectMapper.readValue(request.getBody(), Order.class);
		return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Order Id: "+ order.getId());
		
	}

}
