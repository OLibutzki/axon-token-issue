package de.libutzki.axon.token;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("TrackingEvent2Handler")
public class TrackingEvent2Handler {

	@EventHandler
	void eventHandler(Event2 event2) {
	}
}
