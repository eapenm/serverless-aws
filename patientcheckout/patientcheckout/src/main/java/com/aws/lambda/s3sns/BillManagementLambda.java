package com.aws.lambda.s3sns;

import java.awt.Event;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.aws.lambda.s3sns.modal.PatientCheckoutEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BillManagementLambda {
	ObjectMapper objectMapper = new ObjectMapper();
	private PatientCheckoutEvent patientCheckoutEvent;

	public void handler(SNSEvent event) {
		event.getRecords().forEach(snsRecrod -> {
			try {
				patientCheckoutEvent = objectMapper.readValue(snsRecrod.getSNS().getMessage(),
						PatientCheckoutEvent.class);
				System.out.println(patientCheckoutEvent);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		});

	}

}
