package com.aws.lambda.studentgrading.modal;

public class Student {
	private String rollNo;
	private String name;
	private double testScore;
	private String grade;
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getTestScore() {
		return testScore;
	}
	public void setTestScore(double testScore) {
		this.testScore = testScore;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Student(String rollNo, String name, double testScore, String grade) {
		super();
		this.rollNo = rollNo;
		this.name = name;
		this.testScore = testScore;
		this.grade = grade;
	}
	@Override
	public String toString() {
		return "Student [rollNo=" + rollNo + ", name=" + name + ", testScore=" + testScore + ", grade=" + grade + "]";
	}
	public Student() {
		
	}

}
