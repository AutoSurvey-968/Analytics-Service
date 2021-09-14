package com.revature.autosurvey.analytics.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.util.json.Jackson;
import com.revature.autosurvey.analytics.beans.Response;

import lombok.Data;

@Data
@Component
public class MessageSender {

	/**
	 * NOTE: Messaging has been implemented here to allow for GET requests to 
	 * work, but this is not the ideal use case for messaging queues. Data 
	 * retrieved through the GET requests in this application tends to exceed 1
	 * MB in size. AWS SQS, which we have used as our messaging platform, only
	 * allows messages of size 256 KB or less. Thus, unless we use some overly
	 * complex workaround, survey data can NOT, in general, be sent via a
	 * message alone. Storing the data in S3 and using a message to get the 
	 * analytics service to check S3 is a potential solution we discovered near
	 * the end of our sprint, but it was too late to implement.   
	 */
	
	private final QueueMessagingTemplate queueMessagingTemplate;
	private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
	private MessageBuilder<String> builder;

	private Logger log = LoggerFactory.getLogger(MessageSender.class);

	@Autowired
	public MessageSender(AmazonSQSAsync sqs) {
		this.queueMessagingTemplate = new QueueMessagingTemplate(sqs);
	}

	//@Async
	public String sendSurveyId(String queueName, String surveyId) {
		Message<String> message = MessageBuilder.withPayload(Jackson.toJsonString(surveyId)).build();
		this.queueMessagingTemplate.send(queueName, message);
		if (message.getHeaders().getId() != null) {
			UUID id = message.getHeaders().getId();
			if (id == null) 
				return null;
			return id.toString();
		} else
			return null;
	}

	//@Async
	public String sendResponseMessage(String surveyId, Optional<String> week,
			Optional<String> batch) {
		Response r = new Response(surveyId);
		if (week.isPresent()) {
			try {
				Date d = dateTimeFormat.parse(week.get());
				r.setDate(d);
			} catch (ParseException e) {
				log.error(e.toString());
			}
		}
		if (batch.isPresent()) {
			r.setBatch(batch.get());
		}
		Message<String> message = MessageBuilder.withPayload(Jackson.toJsonString(r)).build();
		this.queueMessagingTemplate.send(SQSQueueNames.SUBMISSIONS_QUEUE, message);
		if (message.getHeaders().getId() != null) {
			UUID id = message.getHeaders().getId();
			if (id == null) {
				return null;
			}
			log.trace("message sent");
			return id.toString();
		} else
			return null;
	}
}
