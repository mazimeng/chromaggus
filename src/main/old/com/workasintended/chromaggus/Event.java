package com.workasintended.chromaggus;

public class Event {
	protected EventName name;
	protected Object argument = null;

	public Event() {

	}

	public Event(EventName name) {
		this.name = name;
	}

	public Event(EventName name, Object argument) {
		this.name = name;
		this.argument = argument;
	}

	public <T> T getArgument(Class<T> type) {
		return type.cast(argument);
	}
	public void setArgument(Object argument) {
		this.argument = argument;
	}

	public EventName getName() {
		return name;
	}

	public void setName(EventName name) {
		this.name = name;
	}

	public <T> T cast(Class<T> type) {
        if(!(type.isInstance(this))) return null;
		return type.cast(this);
	}

	public <T> T cast() {
		return (T)this;
	}

	public boolean is(EventName eventName) {
		return eventName == this.name;
	}
}
