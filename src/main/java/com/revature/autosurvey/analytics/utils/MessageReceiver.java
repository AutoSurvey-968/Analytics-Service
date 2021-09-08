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

import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.revature.autosurvey.analytics.beans.Response;
import com.revature.autosurvey.analytics.beans.Survey;

@Component
public class MessageReceiver {
	
	private List<Survey> messageData;
	private List<List<Response>> responseData;
	private ObjectMapper mapper;
	private Logger log = LoggerFactory.getLogger(MessageReceiver.class);
	
	@Autowired
	public MessageReceiver() {
		messageData = new ArrayList<>();
	}
	
	@SqsListener(value = SQSQueueNames.SURVEY_QUEUE, deletionPolicy=SqsMessageDeletionPolicy.ON_SUCCESS)
	public void receiveMessageSurvey(Message<String> message) {
		messageData.add(Jackson.fromJsonString(message.getPayload(), Survey.class)); 
	}
	
    @SqsListener(value = SQSQueueNames.SUBMISSIONS_QUEUE, deletionPolicy=SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessageResponse(Message<String> message) {
        TypeFactory typeFactory = mapper.getTypeFactory();
        try {
            List<Response> list = mapper.readValue(message.getPayload(), typeFactory.constructCollectionType(List.class, Response.class));
            responseData.add(list);
        } catch (JsonProcessingException e) {
            log.error(e.toString());
        }
    }
}