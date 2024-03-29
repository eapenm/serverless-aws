package com.aws.lambda.apis;

import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.lambda.apis.modal.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadOrdersLambda {
	private final ObjectMapper objectMapper =	new ObjectMapper();
	public APIGatewayProxyResponseEvent getOrder(APIGatewayProxyRequestEvent request) throws JsonMappingException, JsonProcessingException {

		//Order order = new Order(123,"Mac Book Pro",100);
		//ctrl+1 to create a new local variable
		AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
		ScanResult scanResults = dynamoDB.scan(new ScanRequest().withTableName(System.getenv("ORDERS_TABLE")));
		List<Order> orders = scanResults.getItems().stream().map(item -> new Order(Integer.parseInt(item.get("id").getN()),
				                                              item.get("itemName").getS(),
				                                              Integer.parseInt(item.get("quantity").getN())))
				                                              .collect(Collectors.toList());
		
		String jsonOutput = objectMapper.writeValueAsString(orders);
		return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(jsonOutput);
		
	}
}
