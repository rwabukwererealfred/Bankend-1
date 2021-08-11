package com.BankEnd1.dto;

public class ResponseMessage {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ResponseMessage(String name) {
		super();
		this.name = name;
	}
	
	public ResponseMessage() {
		
	}
}
