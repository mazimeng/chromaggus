package com.workasintended.chromaggus.job

/**
  * Created by mazimeng on 7/27/17.
  */
class Timer(val duration: Float) extends Job {
  tickInterval = duration
  onTick = Some(() => {
    complete()
  })
}
