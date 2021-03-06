package com.workasintended.chromaggus.behavior

import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.workasintended.chromaggus.Blackboard

/**
  * Created by mazimeng on 7/28/17.
  */
class ReceivedOrder extends LeafTask[Blackboard] {
  override def execute(): Status = {
    if(getObject.orderComponent.has(getObject.entity)) {
      Status.SUCCEEDED
    }
    else {
      Status.FAILED
    }
  }

  override def copyTo(task: Task[Blackboard]): Task[Blackboard] = task
}