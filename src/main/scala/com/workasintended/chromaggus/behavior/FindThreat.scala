package com.workasintended.chromaggus.behavior

import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.badlogic.gdx.ai.btree.Task.Status
import com.workasintended.chromaggus.Blackboard

/**
  * Created by mazimeng on 7/27/17.
  */
class FindThreat extends LeafTask[Blackboard]{
  override def execute(): Status = {
    Status.SUCCEEDED
  }

  override def copyTo(task: Task[Blackboard]): Task[Blackboard] = task
}