package com.revature.autosurvey.analytics.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

import lombok.Data;

@Data
@Component
public class MessageReceiver {

	private List<Message> messageData;
	private final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
	private Logger log = LoggerFactory.getLogger(MessageReceiver.class);

	@Autowired
	public MessageReceiver() {
		messageData = new ArrayList<>();
	}

	@Async
	public CompletableFuture<Message> receive(UUID id) {
		Message m = new Message();
		ReceiveMessageRequest request = new ReceiveMessageRequest().withQueueUrl(SQSQueueNames.ANALYTICS_QUEUE)
				.withWaitTimeSeconds(20);
		Optional<Message> message = sqs.receiveMessage(request).getMessages().stream()
				.filter(p -> p.getMessageId().equals(id.toString())).findFirst();
		if (message.isPresent()) {
			m = message.get();
		} else {
			m.setBody("nothing here");
		}
		log.trace(m.getBody());
		DeleteMessageRequest delete = new DeleteMessageRequest().withQueueUrl(SQSQueueNames.ANALYTICS_QUEUE)
				.withReceiptHandle(m.getReceiptHandle());
		sqs.deleteMessage(delete);
		return CompletableFuture.completedFuture(m);
	}

}