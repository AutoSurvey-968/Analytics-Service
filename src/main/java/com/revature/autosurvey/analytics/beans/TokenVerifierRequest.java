package com.revature.autosurvey.analytics.beans;

import lombok.Data;

@Data
public class TokenVerifierRequest {
	private String token;
	private boolean returnSecureToken;
	
	public TokenVerifierRequest() {
		super();
	}
}
