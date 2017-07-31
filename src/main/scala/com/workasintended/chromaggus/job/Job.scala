package com.workasintended.chromaggus.job

/**
  * Created by mazimeng on 7/26/17.
  */
abstract class Job {
  var done = false
  var started = false
  var onDone: () => Unit = () => {}
  var onStart: () => Unit = () => {}

  def complete(): scala.Unit = {
    if (done) return

    done = true
    onDone()
  }

  def start(): scala.Unit = {
    if(started) return
    started = true
    onStart()
  }

  def update(delta: Float): scala.Unit = {
    start()
  }
}
