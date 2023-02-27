package com.aws.lambda.studentgrading;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.aws.lambda.studentgrading.modal.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReportGenerator {
	ObjectMapper objectMapper=new ObjectMapper();
	 public void handler(SNSEvent event) {
		 event.getRecords().forEach(record->{
			 try {
				Student student= objectMapper.readValue(record.getSNS().getMessage(), Student.class);
				if (student.getTestScore()>= 90) {
					student.setGrade("A");
				}else if (student.getTestScore()< 90 && student.getTestScore()>= 80 )  {
					student.setGrade("B");
				}else if (student.getTestScore()< 80 && student.getTestScore()>= 70 )  {
					student.setGrade("C");
				}else if (student.getTestScore()< 70 && student.getTestScore()>= 60 )  {
					student.setGrade("D");
				}else if (student.getTestScore()< 60 && student.getTestScore()>= 40 )  {
					student.setGrade("E");
				}else {
					student.setGrade("F");
				}
				
				System.out.println(student);
				
				
				
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		 });
		 
	 }

}
