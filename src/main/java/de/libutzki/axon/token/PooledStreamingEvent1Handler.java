package de.libutzki.axon.token;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("PooledStreamingEvent1Handler")
public class PooledStreamingEvent1Handler {

	@EventHandler
	void eventHandler(Event1 event1) {
	}
}
