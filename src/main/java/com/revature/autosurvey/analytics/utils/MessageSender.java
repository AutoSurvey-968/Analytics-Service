package com.revature.autosurvey.analytics.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.util.json.Jackson;
import com.revature.autosurvey.analytics.beans.Response;

import lombok.Data;

@Data
@Component
public class MessageSender {

	private final QueueMessagingTemplate queueMessagingTemplate;
	private final SimpleDateFormat dateTimeFormat = new 
			SimpleDateFormat("yyyy-MM-dd");
	private MessageBuilder<String> builder;
	
	private Logger log = LoggerFactory.getLogger(MessageSender.class);
	
	@Autowired
	public MessageSender(AmazonSQSAsync sqs) {
		this.queueMessagingTemplate = new QueueMessagingTemplate(sqs);
	}

	@Async
	public CompletableFuture<String> sendSurveyId(String queueName, String surveyId) {
		Message<String> message = MessageBuilder.withPayload(Jackson.toJsonString(surveyId)).build();
		this.queueMessagingTemplate.send(queueName, message);
		return CompletableFuture.completedFuture(message.getHeaders().get("MessageId").toString());

	}
	
	@Async
    public CompletableFuture<String> sendResponseMessage(String surveyId, Optional<String> week, Optional<String> batch) {
        Response r = new Response(surveyId);
        if(week.isPresent()) {
            try {
                Date d = dateTimeFormat.parse(week.get());
                r.setDate(d);
            }catch(ParseException e) {
                log.error(e.toString());
            }
        }
        if(batch.isPresent()) {
            r.setBatch(batch.get());
        }
        Message<String> message = MessageBuilder.withPayload(Jackson.toJsonString(r)).build();
        this.queueMessagingTemplate.send(SQSQueueNames.SUBMISSIONS_QUEUE, message);
        return CompletableFuture.completedFuture(message.getHeaders().get("MessageId").toString());
	}
}
