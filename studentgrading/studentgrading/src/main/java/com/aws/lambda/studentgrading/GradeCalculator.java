package com.aws.lambda.studentgrading;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.aws.lambda.studentgrading.modal.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GradeCalculator {
	AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
	ObjectMapper objectMapper=new ObjectMapper();
	private final AmazonSNS sns = AmazonSNSClientBuilder.defaultClient();
	private static final String STUDENT_TOPIC = System.getenv("STUDENT_TOPIC");

	public void handler(S3Event event,Context context) {
		// LambdaLogger logger = context.getLogger();  //we will comment lambda logger and use logger factory.
		Logger logger = LoggerFactory.getLogger(getClass());
		event.getRecords().forEach(record -> {
			S3ObjectInputStream s3inputStream = s3
					.getObject(record.getS3().getBucket().getName(), record.getS3().getObject().getKey())
					.getObjectContent();
			try {
				//logger.log("Reading from S3"); logger factory does not have .log function.
				logger.info("Reading from S3");
				List<Student> studentEvents =Arrays.asList(objectMapper.readValue(s3inputStream, Student[].class));
				System.out.println(studentEvents);
				logger.info("Message being written to SNS");
				s3inputStream.close();
				studentEvents.forEach(studRecord ->{
					try {
						sns.publish(STUDENT_TOPIC,objectMapper.writeValueAsString(studRecord));
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			}catch (JsonMappingException e) {
//				StringWriter stringWriter = new StringWriter();
//				e.printStackTrace(new PrintWriter(stringWriter));
//				logger.info(stringWriter.toString());
				logger.error("Exception is: ", e);
				throw new RuntimeException("Error while processing",e);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		});

	}
}
