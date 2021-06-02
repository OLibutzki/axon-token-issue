package de.libutzki.axon.token;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("PooledStreamingEvent2Handler")
public class PooledStreamingEvent2Handler {

	@EventHandler
	void eventHandler(Event2 event2) {
	}
}
