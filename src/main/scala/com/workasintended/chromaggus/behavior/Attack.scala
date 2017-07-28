package com.workasintended.chromaggus.behavior

import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.workasintended.chromaggus.Blackboard
import com.workasintended.chromaggus.component.JobComponent
import com.workasintended.chromaggus.job.Use

/**
  * Created by mazimeng on 7/27/17.
  */
class Attack extends LeafTask[Blackboard]{
  override def execute(): Status = {
    if(getObject.enemies.nonEmpty) {
      if(getStatus != Status.RUNNING) {
        val attack = new Use(getObject.entity, getObject.enemies.head)
        val jobComponent = new JobComponent(attack)

        getObject.entity.add(jobComponent)
        println("attacking")
      }

      Status.RUNNING
    }
    else Status.SUCCEEDED
  }

  override def copyTo(task: Task[Blackboard]): Task[Blackboard] = task
}