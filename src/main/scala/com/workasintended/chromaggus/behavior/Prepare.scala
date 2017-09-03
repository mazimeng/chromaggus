package com.workasintended.chromaggus.behavior

import com.badlogic.gdx.ai.GdxAI
import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.workasintended.chromaggus.Blackboard

/**
  * Created by mazimeng on 7/27/17.
  */
class Prepare() extends LeafTask[Blackboard]{
  var startTime: Float = 0f

  override def execute(): Status = {
    val oc = getObject.orderComponent.get(getObject.entity)
    if (oc.ability.isEmpty) {
      return Status.FAILED
    }

    val ac = getObject.abilityComponent.get(oc.ability.get)

    if (GdxAI.getTimepiece.getTime - startTime < ac.preparation) Status.RUNNING
    else Status.SUCCEEDED
  }

  override def start(): Unit = {
    startTime = GdxAI.getTimepiece.getTime
  }

  override def copyTo(task: Task[Blackboard]): Task[Blackboard] = task
}
