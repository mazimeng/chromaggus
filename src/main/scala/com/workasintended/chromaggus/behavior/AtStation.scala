package com.workasintended.chromaggus.behavior

import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.workasintended.chromaggus.Blackboard

/**
  * Created by mazimeng on 7/27/17.
  */
class AtStation extends LeafTask[Blackboard]{
  override def execute(): Status = {
    if(getObject.isStationed) Status.SUCCEEDED
    else Status.FAILED
  }

  override def copyTo(task: Task[Blackboard]): Task[Blackboard] = task
}