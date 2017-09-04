package com.workasintended.chromaggus.behavior

import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.workasintended.chromaggus.Blackboard
import com.workasintended.chromaggus.component.OrderComponent
import com.workasintended.chromaggus.system.AbilitySystem

/**
  * Created by mazimeng on 7/28/17.
  */
class UseAbility extends LeafTask[Blackboard] {
  override def execute(): Status = {
    val oc = getObject.orderComponent.get(getObject.entity)
    val ac = getObject.abilityComponent.get(oc.ability.get)

    if (oc.target.isEmpty) {
      getObject.entity.remove(classOf[OrderComponent])
      return Status.SUCCEEDED
    }

    val abs = getObject.engine.getSystem(classOf[AbilitySystem])
    abs.useAbility(oc.ability.get, getObject.entity, oc.target.get)

    if(!ac.repeat) {
      getObject.entity.remove(classOf[OrderComponent])
    }

    Status.SUCCEEDED
  }
  override def copyTo(task: Task[Blackboard]): Task[Blackboard] = task
}