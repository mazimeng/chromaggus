package com.workasintended.chromaggus.job

/**
  * Created by mazimeng on 7/26/17.
  */
abstract class Job {
  protected var isDone = false

  def done(): Boolean = {
    isDone
  }

  def update(delta: Float): scala.Unit
}
