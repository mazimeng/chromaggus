package com.workasintended.chromaggus.behavior

import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.workasintended.chromaggus.Blackboard

/**
  * Created by mazimeng on 7/27/17.
  */
class Dummy() extends LeafTask[Blackboard]{
  var text: String = "dummy"
  override def execute(): Status = {
    println(s"behavior: ${text}")

    Status.SUCCEEDED
  }

  override def copyTo(task: Task[Blackboard]): Task[Blackboard] = task
}
