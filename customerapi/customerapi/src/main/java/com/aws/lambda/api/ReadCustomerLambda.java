package com.aws.lambda.api;

import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.lambda.api.modal.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadCustomerLambda {
	private final ObjectMapper objectMapper =	new ObjectMapper();
	private final AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
	public APIGatewayProxyResponseEvent getCustomer() throws JsonProcessingException {
		ScanResult scanResults = dynamoDB.scan(new ScanRequest().withTableName(System.getenv("CUSTOMER_TABLE")));
		List<Customer> customer = scanResults.getItems().stream().map(item -> new Customer(item.get("firstName").getS(),
                item.get("lastName").getS(),
                Integer.parseInt(item.get("rewardPoint").getN())))
                .collect(Collectors.toList());
		String jsonOutput = objectMapper.writeValueAsString(customer);
		return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(jsonOutput);
		
	}

}
