package com.workasintended.chromaggus.job

/**
  * Created by mazimeng on 7/26/17.
  */
abstract class Job {
  var done = false
  var started = false
  var listener: JobListener = new JobListener {}

  def complete(): scala.Unit = {
    if (done) return

    done = true
    listener.onDone()
  }

  def start(): scala.Unit = {
    if(started) return
    started = true
    listener.onStart()
  }

  def update(delta: Float): scala.Unit = {
    start()
  }
}
