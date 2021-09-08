package com.revature.autosurvey.analytics.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.util.json.Jackson;
import com.revature.autosurvey.analytics.beans.Survey;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MessageReceiver {
	
	private List<UUID> messageData;
	private ObjectMapper mapper;

	@Autowired
	public MessageReceiver() {
		messageData = new ArrayList<>();
		mapper = new ObjectMapper();
	}
	
	@SqsListener(value = SQSQueueNames.SURVEY_QUEUE)
	public Survey receiveMessageSurvey(Message<String> message) {
		messageData.add(message.getHeaders().getId());
		return Jackson.fromJsonString(message.getPayload(), Survey.class); 
	}
	
}