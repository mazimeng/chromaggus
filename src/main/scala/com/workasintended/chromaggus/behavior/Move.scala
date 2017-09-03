package com.workasintended.chromaggus.behavior

import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.badlogic.gdx.ai.btree.Task.Status
import com.workasintended.chromaggus.Blackboard
import com.workasintended.chromaggus.component.JobComponent
import com.workasintended.chromaggus.job.MoveTo

class Move extends LeafTask[Blackboard] {
  override def execute(): Status = {
    val oc = getObject.orderComponent.get(getObject.entity)
    val ac = getObject.abilityComponent.get(oc.ability.get)

    if (oc.position.isEmpty) {
      return Status.FAILED
    }

    if (getStatus != Status.RUNNING) {
      val moveTo = new MoveTo(getObject.entity, oc.position.get, ac.range2)
      val jobComponent = new JobComponent(moveTo)

      getObject.entity.add(jobComponent)
    }

    Status.RUNNING
  }

  override def copyTo(task: Task[Blackboard]): Task[Blackboard] = task
}