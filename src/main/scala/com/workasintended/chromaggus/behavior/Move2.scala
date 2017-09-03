//package com.workasintended.chromaggus.behavior
//
//import com.badlogic.gdx.ai.btree.Task.Status
//import com.workasintended.chromaggus.component.JobComponent
//import com.workasintended.chromaggus.job.{Follow, MoveTo}
//import com.workasintended.chromaggus.system.AbilitySystem
//
//class Move2 extends OrderExecution {
//  override def onOrder(): Status = {
//    val oc = getObject.orderComponent.get(getObject.entity)
//    val pc = getObject.positionComponent.get(getObject.entity)
//    val ac = getObject.abilityComponent.get(oc.ability.get)
//
//    val abs = getObject.engine.getSystem(classOf[AbilitySystem])
//    if (oc.position.isDefined) {
//      if (abs.isInRange(oc.ability.get, getObject.entity, oc.position.get)) {
//        return Status.SUCCEEDED
//      }
//      else {
//        if(getStatus != Status.RUNNING) {
//          val moveTo = new MoveTo(getObject.entity, oc.position.get, ac.range2)
//          val jobComponent = new JobComponent(moveTo)
//
//          getObject.entity.add(jobComponent)
//        }
//        return Status.RUNNING
//      }
//    }
//    else if (oc.target.isDefined) {
//      if (abs.isInRange(oc.ability.get, getObject.entity, oc.target.get)) {
//        return Status.SUCCEEDED
//      }
//      else {
//        if(getStatus != Status.RUNNING) {
//          val follow = new Follow(getObject.entity, oc.target.get, ac.range2)
//          val jobComponent = new JobComponent(follow)
//
//          getObject.entity.add(jobComponent)
//        }
//
//        return Status.RUNNING
//
//      }
//
//    }
//
//    Status.FAILED
//  }
//}