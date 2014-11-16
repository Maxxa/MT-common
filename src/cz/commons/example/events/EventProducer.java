package cz.commons.example.events;

import com.google.common.eventbus.EventBus;

/***
 * Producer should be the data structure. It holds reference to eventbus and
 * pushes events into it
 * 
 * @author Viktor
 *
 */
public class EventProducer {

	private EventBus eventBus;

	public EventProducer(EventBus eventBus) {
		this.eventBus = eventBus;


	}

	public void produce() {

		for (int i = 0; i < 20; i++) {
			eventBus.post(new CreateEvent(i));
		}

		eventBus.post(new SearchEvent(10));
		eventBus.post(new RemoveEvent(5));

	}
	

}
