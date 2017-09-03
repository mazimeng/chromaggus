package com.workasintended.chromaggus.job

/**
  * Created by mazimeng on 7/26/17.
  */
abstract class Job {
  var done = false
  var started = false
  var onDone: () => Unit = () => {}
  var onStart: () => Unit = () => {}
  var onTick: Option[() => Unit] = None
  var tickInterval: Float = Float.MaxValue
  var elapsed: Float = 0f

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
    elapsed += delta
    start()
    if(onTick.isDefined && elapsed >= tickInterval) {
      onTick.get.apply()
      elapsed = 0f
    }
  }
}
