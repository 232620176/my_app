package com.hydra.maven.exception;

public class AccountEmailException extends Exception {
	private static final long serialVersionUID = 8161445715248848963L;
	public AccountEmailException (String msg, Throwable t){
		super(msg, t);
	}
}
