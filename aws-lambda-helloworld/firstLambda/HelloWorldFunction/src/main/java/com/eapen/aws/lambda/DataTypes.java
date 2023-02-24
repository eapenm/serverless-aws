package com.eapen.aws.lambda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;


public class DataTypes {
	
	private Double instanceVariable = Math.random();
	private static Double staticVariable = Math.random();
	
	
	
	public DataTypes() {
		System.out.println("Inside Constructor");
	}
	static {
		System.out.println("Static Block Executed");
	}
	
	public void coldStartBasics() {
		Double localVariable = Math.random();
		System.out.println("Instance: "+instanceVariable+" Static Variable: "+staticVariable+" local Variable: "+localVariable);
	}

	public int getNumber(float number) {
		return (int) number;
	}

	public List<Integer> getScore(List<String> names) {
		Map<String, Integer> studentScore = new HashMap();
		studentScore.put("Robin", 90);
		studentScore.put("John", 100);
		studentScore.put("Deepa", 99);

		List<Integer> matchingScores = new LinkedList();
		names.forEach(name -> {
			matchingScores.add(studentScore.get(name));

		});
		return matchingScores;
	}
	
	public void saveEmployeeData(Map<String,Integer> empData) {
		System.out.println(empData);
		}
	public Map<String,List<Integer>> getStudentScores(){
		Map<String,List<Integer>> studentScores = new HashMap<>();
		studentScores.put("Robin", Arrays.asList(80,90,95));
		studentScores.put("Bin", Arrays.asList(85,95,96));
		studentScores.put("Joe", Arrays.asList(87,97,97));
		return studentScores;
		}
	public ClinicalData getClinicalData(Patient patient) {
		System.out.println(patient.getName());
		System.out.println(patient.getSsn());
		ClinicalData clinicalData = new ClinicalData();
		clinicalData.setBp("80/120");
		clinicalData.setHeartRate("100");
		return clinicalData;
	}
	public void getOutput(InputStream input,OutputStream output, Context context) throws IOException {
		System.out.println(System.getenv("restapiurl"));
		System.out.println(context.getAwsRequestId());
		System.out.println(context.getLogGroupName());
		System.out.println(context.getRemainingTimeInMillis());
		System.out.println(context.getMemoryLimitInMB());
		System.out.println(context.getFunctionName());
		
		int data;
		while ((data = input.read()) != -1) {
			output.write(Character.toLowerCase(data));
		}
		
	}
}
