package com.workasintended.chromaggus.event

import java.util.Observable

class Event[T] extends Observable {
  def fire(arg: T): Unit = {
    setChanged()
    notifyObservers(arg)
  }
}

