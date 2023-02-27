package com.aws.lambda.s3sns;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.aws.lambda.s3sns.modal.PatientCheckoutEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PatientCheckoutLambda {
	private static final String PATIENT_CHECKOUT_TOPIC = System.getenv("PATIENT_CHECKOUT_TOPIC");
	private final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
	private S3ObjectInputStream s3InputStream;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final AmazonSNS sns = AmazonSNSClientBuilder.defaultClient();

	public void handler(S3Event event) {
		event.getRecords().forEach(record -> {
			s3InputStream = s3.getObject(record.getS3().getBucket().getName(), record.getS3().getObject().getKey())
					.getObjectContent();
			try {
				List<PatientCheckoutEvent> patientCheckoutEvent = Arrays
						.asList(objectMapper.readValue(s3InputStream, PatientCheckoutEvent[].class));
				System.out.println(patientCheckoutEvent);
				s3InputStream.close();
				publishMessageToSNS(patientCheckoutEvent);
			} catch (StreamReadException e) {
				e.printStackTrace();
			} catch (DatabindException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}

	private void publishMessageToSNS(List<PatientCheckoutEvent> patientCheckoutEvent) {
		patientCheckoutEvent.forEach(checkOutEvent -> {
			try {
				sns.publish(PATIENT_CHECKOUT_TOPIC, objectMapper.writeValueAsString(checkOutEvent));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

}
