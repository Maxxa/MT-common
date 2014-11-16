package cz.commons.example.events;

 abstract class Event {
	int key;
	public Event(int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
	}
}

class CreateEvent extends Event {
	public CreateEvent(int key) {
		super(key);
	}
	
}

class SearchEvent extends Event {
	public SearchEvent(int key) {
		super(key);
	}

}

class RemoveEvent extends Event {
	public RemoveEvent(int key) {
		super(key);
	}
}