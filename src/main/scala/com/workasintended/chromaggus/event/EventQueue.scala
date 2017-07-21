package com.workasintended.chromaggus.event

import java.util


class EventQueue {
  private val events = new util.LinkedList[Event]()
  private val handlers = new util.HashMap[String, Option[util.LinkedList[EventHandler]]]()

  def handle(delta: Float) {
    while (!events.isEmpty) {
      val event = events.pop()
      val eventHandlers = handlers.get(event.name)
      if (!eventHandlers.isEmpty) {
        eventHandlers.foreach(list => { for(h <- list)  println(h) })
        for (handler <- eventHandlers) {
          handler.handle(event)
        }
      }
    }
  }

  def register(name: Nothing, handler: Nothing) {
    //		LinkedList<EventHandler> handlerList = null;
    //		if(!this.handlers.containsKey(name)) {
    //			handlerList = new LinkedList<EventHandler>();
    //			this.handlers.put(name, handlerList);
    //		}
    //		else {
    //
    //		}
    var handlerList = this.handlers.get(name)
    if (handlerList == null) handlerList = new Nothing
    this.handlers.put(name, handlerList)
    handlerList.add(handler)
  }

  @Override def enqueue(event: Nothing) {
    this.events.add(event)
  }
}
