package com.aws.lambda.studentgrading.sqs;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;

public class ClaimManagementLambda {
	public void handler(SQSEvent event) {
		event.getRecords().forEach(record->{
			System.out.println(record.getBody());
		});
		
	}

}
