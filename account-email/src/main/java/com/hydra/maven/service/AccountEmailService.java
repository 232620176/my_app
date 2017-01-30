package com.hydra.maven.service;

import com.hydra.maven.exception.AccountEmailException;

public interface AccountEmailService {
	public void sendMail(String to, String subject, String htmlText)
		throws AccountEmailException;
}
