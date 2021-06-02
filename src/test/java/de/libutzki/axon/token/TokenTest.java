package de.libutzki.axon.token;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;

import org.axonframework.eventhandling.gateway.EventGateway;
import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayNameGeneration(ReplaceUnderscores.class)
class TokenTest {

	@Autowired
	private EventGateway eventGateway;

	@Autowired
	private EntityManager entityManager;

	@Test
	void assert_that_TrackingEventProcessor_that_handles_the_published_event_updates_the_token() {
		eventGateway.publish(new Event1("Test"));
		await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> assertThatTokenIsSetToIndexForEventProcessor("TrackingEvent1Handler", 1));

	}

	@Test
	void assert_that_TrackingEventProcessor_that_does_NOT_handle_the_published_event_updates_the_token() {
		eventGateway.publish(new Event1("Test"));
		await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> assertThatTokenIsSetToIndexForEventProcessor("TrackingEvent2Handler", 1));
	}

	@Test
	void assert_that_PooledStreamingEventProcessor_that_handles_the_published_event_updates_the_token() {
		eventGateway.publish(new Event1("Test"));
		await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> assertThatTokenIsSetToIndexForEventProcessor("PooledStreamingEvent1Handler", 1));

	}

	@Test
	void assert_that_PooledStreamingEventProcessor_that_does_NOT_handle_the_published_event_updates_the_token() {
		eventGateway.publish(new Event1("Test"));
		await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> assertThatTokenIsSetToIndexForEventProcessor("PooledStreamingEvent2Handler", 1));
	}
	
	@Test
	void assert_that_PooledStreamingEventProcessor_that_does_NOT_handle_the_published_event_updates_the_token_if_the_previous_token_is_not_null() {
		eventGateway.publish(new Event2("Test"));
		
		await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> assertThatTokenIsSetToIndexForEventProcessor("PooledStreamingEvent2Handler", 1));
		
		eventGateway.publish(new Event1("Test"));
		
		await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> assertThatTokenIsSetToIndexForEventProcessor("PooledStreamingEvent2Handler", 2));
		
	}

	private void assertThatTokenIsSetToIndexForEventProcessor(String eventProcessor, int index) {
		TokenEntry tokenEntry = entityManager.find(TokenEntry.class,
				new TokenEntry.PK(eventProcessor, 0));
		assertThat(tokenEntry).isNotNull();
		assertThat(tokenEntry.getSerializedToken()).isNotNull();
		assertThat(tokenEntry.getSerializedToken().getData()).isNotNull();
		assertThat(new String(tokenEntry.getSerializedToken().getData(), StandardCharsets.UTF_8))
				.isEqualTo("{\"index\":" +index + ",\"gaps\":[]}");
	}

}
