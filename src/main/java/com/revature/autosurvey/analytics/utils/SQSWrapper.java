package com.revature.autosurvey.analytics.utils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.revature.autosurvey.analytics.beans.Response;
import com.revature.autosurvey.analytics.beans.Survey;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * 
 * @author MuckJosh 
 *
 */

public class SQSWrapper {

	private MessageSender sender;
	private MessageReceiver receiver;
	private ObjectMapper mapper;
	
	private Logger log = LoggerFactory.getLogger(SQSWrapper.class);
	
	@Autowired
	public SQSWrapper(MessageSender sender, MessageReceiver receiver, ObjectMapper mapper) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.mapper = mapper;
	}

	
	@Async
	public Mono<Survey> getSurvey(String surveyId) {
		final UUID sentMessageId;
		try {
			sentMessageId = sender.sendSurveyId(SQSQueueNames.SURVEY_QUEUE, surveyId).get(3, TimeUnit.SECONDS);
			return Flux.fromIterable(receiver.getMessageData()).filter(message -> {
				if (!sentMessageId.equals(null)) {
					if (sentMessageId.equals(message.getHeaders().get("MessageId"))) {
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
	/**
	 * 
	 * @param surveyId	surveyId for responses
	 * @param week		optional week date for searching
	 * @param batch		optional batch name for searching
	 * @return 			returns a flux of responses for the service to handle
	 */
	@Async
	public Flux<Response> getResponses(String surveyId, Optional<String> week, Optional<String> batch){
		UUID sentMessageId;
		try {
			sentMessageId = sender.sendResponseMessage(surveyId, week, batch).get(3, TimeUnit.SECONDS);
			return Flux.fromIterable(receiver.getMessageData()).filter(message -> sentMessageId.toString().equals(message.getHeaders().get("MessageId")))
					.map(messages -> {
						try {
							return mapper.readValue(messages.getPayload(), mapper.getTypeFactory().constructCollectionLikeType(List.class, Response.class));
						} catch (JsonProcessingException e) {
							log.error("Json Error: \n", e);
							return null;
						}
					});
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			
			log.error("Thread Error: \n", e);
			return Flux.empty();
		}
		
		
	}
	
}
