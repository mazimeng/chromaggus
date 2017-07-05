package com.workasintended.ai.behavior

import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.badlogic.gdx.ai.btree.Task.Status
import com.workasintended.chromaggus.ai.behavior.Blackboard


/**
  * Created by mazimeng on 5/22/16.
  */
class WithinRadius(radiusCheck: (Blackboard) => Boolean = _) extends LeafTask[Blackboard] {
  override def execute: Task.Status = {
    if (radiusCheck(getObject)) Status.SUCCEEDED
    else Status.FAILED
  }

  private def defaultRadiusCheck(bb: Blackboard): Boolean = {
    true
  }

  protected def copyTo(task: Task[Blackboard]): Task[Blackboard] = task

  override def cloneTask = new WithinRadius(radiusCheck)
}
