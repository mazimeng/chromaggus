package com.workasintended.chromaggus;

public interface EventQueue {
	void handle(float delta);
	
	void register(EventName name, EventHandler handler);
	void enqueue(Event event);
}
