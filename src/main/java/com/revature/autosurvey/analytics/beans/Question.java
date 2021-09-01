package com.revature.autosurvey.analytics.beans;

import java.util.List;

import lombok.Data;

@Data
public class Question {
	private QuestionType questionType;
	private String title;
	private String helpText;
	private Boolean isRequired;
	private List<String> choices;
	private Boolean hasOtherOption;

	public enum QuestionType {
		CHECKBOX, DROPDOWN, MULTIPLE_CHOICE, PARAGRAPH, RADIO, SHORT_ANSWER
	}
}
