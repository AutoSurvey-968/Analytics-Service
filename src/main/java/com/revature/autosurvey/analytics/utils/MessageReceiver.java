package com.revature.autosurvey.analytics.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class MessageReceiver {
	
	private List<Message<String>> messageData;
	private Logger log = LoggerFactory.getLogger(MessageReceiver.class);
	
	@Autowired
	public MessageReceiver() {
		messageData = new ArrayList<>();
	}
	
	@SqsListener(value = SQSQueueNames.SURVEY_QUEUE, deletionPolicy=SqsMessageDeletionPolicy.ON_SUCCESS)
	public void receiveMessageSurvey(Message<String> message) {
		messageData.add(message); 
	}
	
    @SqsListener(value = SQSQueueNames.SUBMISSIONS_QUEUE, deletionPolicy=SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessageResponse(Message<String> message) {
        messageData.add(message);
    }
}