package com.workasintended.chromaggus.behavior

import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.badlogic.gdx.math.Vector2
import com.workasintended.chromaggus.Blackboard
import com.workasintended.chromaggus.component.JobComponent
import com.workasintended.chromaggus.job.MoveTo

/**
  * Created by mazimeng on 7/27/17.
  */
class ReturnToStation extends LeafTask[Blackboard]{
  override def execute(): Status = {
    if(getObject.isStationed) Status.SUCCEEDED
    else {
      if(getStatus != Status.RUNNING) {
        val moveTo = new MoveTo(getObject.entity, new Vector2(getObject.station.x, getObject.station.y))
        val jobComponent = new JobComponent(moveTo)

        getObject.entity.add(jobComponent)
        println("returning to station")
      }
      Status.RUNNING
    }
  }

  override def copyTo(task: Task[Blackboard]): Task[Blackboard] = task
}
