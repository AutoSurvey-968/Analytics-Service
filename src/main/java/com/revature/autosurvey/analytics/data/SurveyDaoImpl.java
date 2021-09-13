package com.revature.autosurvey.analytics.data;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.revature.autosurvey.analytics.beans.Survey;

import reactor.core.publisher.Mono;

@Component
public class SurveyDaoImpl implements SurveyDao {
	
	@Autowired
	private WebClient.Builder webClient;

	@Override
	public Mono<Survey> getSurvey(String surveyId) {
		WebClient wc = webClient.baseUrl(System.getenv("GATEWAY_URL")).build();
		return wc.get()
				.uri(uriBuilder -> uriBuilder.pathSegment("surveys", "{surveyId}").build(surveyId))
				.exchangeToMono(res -> {
					if (res.rawStatusCode() == 200) {
						return res.bodyToMono(Survey.class)
								.switchIfEmpty(Mono.just(new Survey()).map(s -> {
									s.setUuid(UUID.fromString(surveyId));
									s.setQuestions(new ArrayList<>());
									return s;
								}));
					}
					return Mono.just(new Survey());
				});

	}

}
