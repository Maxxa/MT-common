package cz.commons.example.events;

import com.google.common.eventbus.EventBus;

public class EventBusExample {

	public static void main(String[] args) {
		EventBus eventBus = new EventBus();

		EventConsumer consumer = new EventConsumer();

		// Registering a consumer class to event bus -
		// specifying what class will handle produced events.
		eventBus.register(consumer);
		EventProducer ep = new EventProducer(eventBus);
		ep.produce();

	}

}
