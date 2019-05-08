package com.kbsystems.finance.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class NewResourceEvent extends ApplicationEvent{
	private static final long serialVersionUID = 1L;
	
	private final HttpServletResponse response;
	private final String id;

	public NewResourceEvent(Object source, HttpServletResponse response, String id) {
		super(source);
		
		this.response = response;
		this.id = id;

	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public String getId() {
		return id;
	}

}
