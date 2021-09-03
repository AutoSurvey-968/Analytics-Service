package com.revature.autosurvey.analytics.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.util.json.Jackson;
import com.revature.autosurvey.analytics.beans.Survey;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.Data;

@Data
@Component
public class MessageSender {

	private final QueueMessagingTemplate queueMessagingTemplate;

	private String queueName;

	@Autowired
	public MessageSender(AmazonSQSAsync sqs) {
		this.queueMessagingTemplate = new QueueMessagingTemplate(sqs);
	}

	@Scheduled(fixedDelay = 5000)
	public void sendObject() {
		Survey survey = new Survey();
		queueName = SQSQueueNames.TEST_QUEUE;
		this.queueMessagingTemplate.send(queueName, MessageBuilder.withPayload(Jackson.toJsonString(survey)).build());
		System.out.println("sending a survey");
	}

	// Jackson.toJsonString(o)
	// new String("Send Test")
}
