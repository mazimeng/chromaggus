package com.workasintended.chromaggus.behavior

import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.badlogic.gdx.ai.btree.Task.Status
import com.workasintended.chromaggus.Blackboard
import com.workasintended.chromaggus.component.JobComponent

class Follow extends LeafTask[Blackboard] {
  override def execute(): Status = {
    val oc = getObject.orderComponent.get(getObject.entity)
    val ac = getObject.abilityComponent.get(oc.ability.get)

    if (oc.target.isEmpty) {
      return Status.FAILED
    }

    if (getStatus != Status.RUNNING) {
      val follow = new com.workasintended.chromaggus.job.Follow(getObject.entity, oc.target.get, ac.range2)
      val jobComponent = new JobComponent(follow)

      getObject.entity.add(jobComponent)
    }

    Status.RUNNING
  }

  override def copyTo(task: Task[Blackboard]): Task[Blackboard] = task
}