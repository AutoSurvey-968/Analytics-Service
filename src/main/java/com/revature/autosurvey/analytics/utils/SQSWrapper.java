package com.revature.autosurvey.analytics.utils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.autosurvey.analytics.beans.Response;
import com.revature.autosurvey.analytics.beans.Survey;

import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @author MuckJosh, siddmohanty111
 *
 */

@Data
@Component
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

	/**
	 * 
	 * @param surveyId The UUID of the survey to be found
	 * @return A Mono of the survey that was found
	 */

	
	public Mono<Survey> getSurvey(String surveyId) {
		//final UUID sentMessageId;
		try {
			String id = sender.sendSurveyId(SQSQueueNames.SURVEY_QUEUE, surveyId);
			UUID sentMessageId = UUID.fromString(id);
			
			return Mono.just(Jackson.fromJsonString(receiver.receive(sentMessageId).get().getBody(),Survey.class));
		} catch (IllegalArgumentException | InterruptedException | ExecutionException e) {
			log.error("surveyid returned was null, could not convert to UUID");
			return Mono.empty();
		}
	}

	/**
	 * 
	 * @param surveyId surveyId for responses
	 * @param week     optional week date for searching
	 * @param batch    optional batch name for searching
	 * @return returns a flux of responses for the service to handle
	 */
	
	public Flux<Response> getResponses(String surveyId, Optional<String> week, Optional<String> batch) {
		System.out.println("here");
		String id = sender.sendResponseMessage(surveyId, week, batch);
		if(id == null) {
			return Flux.empty();
		}
		UUID sentMessageId = UUID.fromString(id);
		
		try {
			return Flux.fromIterable(mapper.readValue(receiver.receive(sentMessageId).get().getBody(),
					mapper.getTypeFactory().constructCollectionLikeType(List.class, Response.class)));
		} catch (JsonProcessingException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Flux.empty();
		}
		
	}

}
