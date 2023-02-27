package com.aws.lambda.api.modal;

public class Customer {
	private String firstName;
	private String lastName;
	private int rewardPoint;

	public Customer() {
		
	}
	public Customer(String firstName, String lastName, int rewardPoint) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.rewardPoint = rewardPoint;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getRewardPoint() {
		return rewardPoint;
	}

	public void setRewardPoint(int rewardPoint) {
		this.rewardPoint = rewardPoint;
	}

}
