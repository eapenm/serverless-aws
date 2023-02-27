package com.aws.lambda.api;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.lambda.api.modal.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateCustomerLambda {
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
	public APIGatewayProxyResponseEvent createCustomer(APIGatewayProxyRequestEvent request) throws JsonMappingException, JsonProcessingException {
		Customer customer = objectMapper.readValue(request.getBody(), Customer.class);
		Table table = dynamoDB.getTable(System.getenv("CUSTOMER_TABLE"));
		Item item = new Item().withPrimaryKey("firstName", customer.getFirstName())
				              .withString("lastName", customer.getLastName())
				              .withInt("rewardPoint", customer.getRewardPoint());
		table.putItem(item);
		
		
		
		return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Customer created"+ customer.getFirstName());
		
	}

}
