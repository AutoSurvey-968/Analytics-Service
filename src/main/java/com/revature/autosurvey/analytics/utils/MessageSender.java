package com.revature.autosurvey.analytics.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.util.json.Jackson;
import com.revature.autosurvey.analytics.beans.Survey;

import lombok.Data;

@Data
@Component
public class MessageSender {

	private final QueueMessagingTemplate queueMessagingTemplate;

	// private String queueName;

	@Autowired
	public MessageSender(AmazonSQSAsync sqs) {
		this.queueMessagingTemplate = new QueueMessagingTemplate(sqs);
	}

	@Scheduled(fixedDelay = 5000)
	public void sendObject() {
		Survey survey = new Survey();
		this.queueMessagingTemplate.send(SQSQueueNames.TEST_QUEUE, MessageBuilder.withPayload(Jackson.toJsonString(survey)).build());
		System.out.println("sending a survey");
	}

	public void sendObject(String surveyId) {
		this.queueMessagingTemplate.send(SQSQueueNames.SUBMISSIONS_QUEUE, MessageBuilder.withPayload(Jackson.toJsonString(surveyId)).build());
		this.queueMessagingTemplate.send(SQSQueueNames.SURVEY_QUEUE, MessageBuilder.withPayload(Jackson.toJsonString(surveyId)).build());

	}
	
}
