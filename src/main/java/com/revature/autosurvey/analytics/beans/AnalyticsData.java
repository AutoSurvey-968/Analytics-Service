package com.revature.autosurvey.analytics.beans;

import lombok.Data;
/**
 * 
 * @author MuckJosh
 * Data class to hold averages and deltas for reports and questions
 * Datum is considered for percentages and averages for the responses.
 * Delta is the change between reports for those averages/percentages.
 */
@Data
public class AnalyticsData {
	private double datum;
	private double delta;
}
