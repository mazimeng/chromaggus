package com.workasintended.chromaggus.event

trait EventHandler {
  def handle(event: Event)
}
