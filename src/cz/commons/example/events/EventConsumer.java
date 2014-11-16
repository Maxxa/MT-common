package cz.commons.example.events;

import com.google.common.eventbus.Subscribe;

public class EventConsumer {

	/*
	 * The methods here are subscribed (via annotation) to recieve messages
	 * (events) from event bus It filteres it by itself (create events goes to
	 * first method only and so on)
	 */

	@Subscribe
	public void handleCreateEvent(CreateEvent ce) {
		System.out.println("Handling create event with key:" + ce.getKey());
	}

	@Subscribe
	public void handleRemoveEvent(RemoveEvent re) {
		System.out.println("Handling remove event with key:" + re.getKey());
	}

	@Subscribe
	public void handleSearchEvent(SearchEvent se) {
		System.out.println("Handling search event with key:" + se.getKey());
	}

	/***
	 * This handles all events who are children (inherits) the abstract event
	 * 
	 * @param e
	 */
	@Subscribe
	public void handlingAllEvents(Event e) {
		System.out.println("Handling GLOBAL event with key:" + e.getKey());
	}
}
