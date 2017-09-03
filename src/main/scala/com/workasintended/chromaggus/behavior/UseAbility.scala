package com.workasintended.chromaggus.behavior

import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.workasintended.chromaggus.Blackboard
import com.workasintended.chromaggus.system.AbilitySystem

/**
  * Created by mazimeng on 7/28/17.
  */
class UseAbility extends LeafTask[Blackboard] {
//  def isInRange: Boolean = {
//    val oc = getObject.orderComponent.get(getObject.entity)
//    val abs = getObject.engine.getSystem(classOf[AbilitySystem])
//
//    abs.isInRange(oc.ability.get,
//      getObject.entity,
//      oc.target.get)
//  }

  override def execute(): Status = {
    val oc = getObject.orderComponent.get(getObject.entity)
    val ac = getObject.abilityComponent.get(oc.ability.get)

    if (oc.target.isEmpty) return Status.FAILED

    val abs = getObject.engine.getSystem(classOf[AbilitySystem])
    abs.useAbility(oc.ability.get, getObject.entity, oc.target.get)
    Status.SUCCEEDED
  }
  override def copyTo(task: Task[Blackboard]): Task[Blackboard] = task
}