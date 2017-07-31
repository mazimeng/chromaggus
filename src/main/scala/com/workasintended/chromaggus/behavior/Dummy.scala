package com.workasintended.chromaggus.behavior

import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.workasintended.chromaggus.Blackboard

/**
  * Created by mazimeng on 7/27/17.
  */
class Dummy(var text: String = "dummy", var statusToReturn: Status = Status.SUCCEEDED) extends LeafTask[Blackboard]{
  def this() {
    this("dummy", Status.SUCCEEDED)
  }

  override def execute(): Status = {
    println(s"behavior: ${text}, ${statusToReturn}")
    statusToReturn
  }

  override def copyTo(task: Task[Blackboard]): Task[Blackboard] = {
    val dummy = task.asInstanceOf[Dummy]
    dummy.text = text
    dummy.statusToReturn = statusToReturn
    return dummy
  }
}