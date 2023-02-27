package com.aws.lambda.studentgrading;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.aws.lambda.studentgrading.modal.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GradeCalculator {
	AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
	ObjectMapper objectMapper=new ObjectMapper();
	private final AmazonSNS sns = AmazonSNSClientBuilder.defaultClient();
	private static final String STUDENT_TOPIC = System.getenv("STUDENT_TOPIC");

	public void handler(S3Event event) {
		event.getRecords().forEach(record -> {
			S3ObjectInputStream s3inputStream = s3
					.getObject(record.getS3().getBucket().getName(), record.getS3().getObject().getKey())
					.getObjectContent();
			try {
				List<Student> studentEvents =Arrays.asList(objectMapper.readValue(s3inputStream, Student[].class));
				System.out.println(studentEvents);
				s3inputStream.close();
				studentEvents.forEach(studRecord ->{
					try {
						sns.publish(STUDENT_TOPIC,objectMapper.writeValueAsString(studRecord));
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		});

	}
}
