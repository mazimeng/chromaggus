package com.workasintended.chromaggus.event

import java.util.{Observable, Observer}

/**
  * Created by mazimeng on 9/1/17.
  */
abstract class EventHandler[T] extends Observer {
  def handle(arg: T)

  override def update(o: Observable, arg: scala.Any): Unit = {
    val changed: T = arg.asInstanceOf[T]

    handle(changed)
  }
}
