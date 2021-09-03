package com.revature.autosurvey.analytics.utils;

import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MessageReceiver {
	ObjectMapper mapper = new ObjectMapper();

	@SqsListener(value = "https://sqs.us-east-1.amazonaws.com/855430746673/TestQueue")
	public void receiveMessage(Message message) {
//        System.out.println(message.getMessageAttributes().get("title"));
		System.out.println(message.getMessageAttributes().entrySet());
//        System.out.println(message.getAttributes().keySet());
		System.out.println(message.getBody());
	}

}