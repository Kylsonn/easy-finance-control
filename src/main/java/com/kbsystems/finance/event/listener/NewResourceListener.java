package com.kbsystems.finance.event.listener;

import java.net.URI;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kbsystems.finance.event.NewResourceEvent;

@Component
public class NewResourceListener implements ApplicationListener<NewResourceEvent>{

	@Override
	public void onApplicationEvent(NewResourceEvent event) {
		URI uri =  ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(event.getId()).toUri();
		event.getResponse().setHeader("Location", uri.toASCIIString());
	}

}
