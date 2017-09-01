package com.workasintended.chromaggus.system

import com.badlogic.ashley.core.EntitySystem
import com.workasintended.chromaggus.event.{Event, EventHandler}

import scala.collection.mutable


class EventSystem extends EntitySystem {
  private val events = mutable.Queue[Event]()
  private val handlers = mutable.Map[String, mutable.Set[EventHandler]]()

  def handle(delta: Float): Unit = {
    while (events.nonEmpty) {
      val event = events.dequeue()
      val eventHandlers = handlers.get(event.name)
      eventHandlers.foreach(h => for (elem <- h) {elem.handle(event)})
    }
  }

  def register(name: String, handler: EventHandler) {
    val option = this.handlers.get(name)

    val handlerSet = option.getOrElse(mutable.Set[EventHandler]())
    handlerSet.add(handler)

    this.handlers.put(name, handlerSet)
  }

  def enqueue(event: Event) {
    this.events.enqueue(event)
  }

  override def update(deltaTime: Float): Unit = {
    handle(deltaTime)
  }
}

