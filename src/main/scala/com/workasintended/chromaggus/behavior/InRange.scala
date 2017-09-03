package com.workasintended.chromaggus.behavior

import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.workasintended.chromaggus.Blackboard

class InRange extends LeafTask[Blackboard] {
  override def execute(): Status = {
    val oc = getObject.orderComponent.get(getObject.entity)
    val pc = getObject.positionComponent.get(getObject.entity)
    val ac = getObject.abilityComponent.get(oc.ability.get)

    if(oc.position.isDefined) {
      if(oc.position.get.dst2(pc.position) <= ac.range2) {
        return Status.SUCCEEDED
      }
//      else {
//        val moveTo = new MoveTo(getObject.entity, oc.position.get, ac.range2)
//        moveTo.onDone = () => {
//          getObject.engine.getSystem(classOf[BehaviorSystem]).think(getObject.entity)
//        }
//
//        val jobComponent = new JobComponent(moveTo)
//
//        getObject.entity.add(jobComponent)
//        return Status.RUNNING
//      }
    }
    else if(oc.target.isDefined) {
      val targetPositionComponent = getObject.positionComponent.get(oc.target.get)
      if(targetPositionComponent.position.dst2(pc.position) <= ac.range2) {
        return Status.SUCCEEDED
      }
    }

    Status.FAILED
  }

  override def copyTo(task: Task[Blackboard]): Task[Blackboard] = task

}