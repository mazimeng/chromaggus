package com.workasintended.chromaggus.behavior

import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.badlogic.gdx.ai.btree.Task.Status
import com.workasintended.chromaggus.Blackboard
import com.workasintended.chromaggus.component.OrderComponent

class OrderComplete extends LeafTask[Blackboard] {
  override def execute(): Status = {
    val oc = getObject.orderComponent.get(getObject.entity)
    val ac = getObject.abilityComponent.get(oc.ability.get)
    if(!ac.repeat) {
      getObject.entity.remove(classOf[OrderComponent])
    }

    Status.SUCCEEDED
  }

  override def copyTo(task: Task[Blackboard]): Task[Blackboard] = task
}