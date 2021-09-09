package com.revature.autosurvey.analytics.utils;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Async;

import com.amazonaws.util.json.Jackson;
import com.revature.autosurvey.analytics.beans.Survey;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SQSWrapper {

	private MessageSender sender;
	private MessageReceiver receiver;
	
	private Logger log = LoggerFactory.getLogger(SQSWrapper.class);
	
	@Async
	public Mono<Survey> getSurvey(String surveyId) {
		final UUID sentMessageId;
		try {
			sentMessageId = sender.sendSurveyId(SQSQueueNames.SURVEY_QUEUE, surveyId).get(3, TimeUnit.SECONDS);
			return Flux.fromIterable(receiver.getMessageData()).filter(message -> {
				if (!sentMessageId.equals(null)) {
					if (sentMessageId.equals(message.getHeaders().get("messageId"))) {
						return true;
					}
				}
				return false;
			}).map(message -> Jackson.fromJsonString(message.getPayload(), Survey.class)).next();
		} catch (TimeoutException e) {
			log.warn("system timed out waiting for response");
			log.warn("timeout: \n", e);
			return Mono.empty();
		} catch (InterruptedException e) {
			log.error("thread interrupted");
			log.error("interrupted: \n", e);
			return Mono.empty();
		} catch (ExecutionException e) {
			log.error("thread execution failed");
			log.error("execution error: \n", e);
			return Mono.empty();
		}	
	}
	
}
