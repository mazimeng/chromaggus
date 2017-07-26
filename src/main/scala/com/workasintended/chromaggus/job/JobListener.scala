package com.workasintended.chromaggus.job

/**
  * Created by mazimeng on 7/26/17.
  */
abstract class JobListener {
  def onDone(): scala.Unit = {}
  def onStart(): scala.Unit = {}
}
