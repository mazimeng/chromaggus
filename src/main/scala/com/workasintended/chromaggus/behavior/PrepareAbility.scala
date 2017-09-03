//package com.workasintended.chromaggus.behavior
//
//import com.badlogic.gdx.ai.btree.{LeafTask, Task}
//import com.badlogic.gdx.ai.btree.Task.Status
//import com.workasintended.chromaggus.Blackboard
//import com.workasintended.chromaggus.component.JobComponent
//import com.workasintended.chromaggus.job.{MoveTo, Timer}
//import com.workasintended.chromaggus.system.AbilitySystem
//
///**
//  * Created by mazimeng on 7/28/17.
//  */
//class PrepareAbility extends LeafTask[Blackboard] {
////  def isInRange: Boolean = {
////    val oc = getObject.orderComponent.get(getObject.entity)
////    val abs = getObject.engine.getSystem(classOf[AbilitySystem])
////
////    abs.isInRange(oc.ability.get,
////      getObject.entity,
////      oc.target.get)
////  }
//
//  override def execute(): Status = {
//    if (getStatus == Status.RUNNING) return Status.RUNNING
//
//    val oc = getObject.orderComponent.get(getObject.entity)
//    val ac = getObject.abilityComponent.get(oc.ability.get)
//
//    if (oc.target.isEmpty) return Status.SUCCEEDED
//
//    val timer = new Timer(ac.preparation)
//    timer.onDone = () => {
//      val abs = getObject.engine.getSystem(classOf[AbilitySystem])
//      if (isInRange) {
//        abs.useAbility(oc.ability.get, getObject.entity, oc.target.get)
//        success()
//      }
//      else {
//        fail()
//      }
//    }
//    val jobComponent = new JobComponent(timer)
//
//    getObject.entity.add(jobComponent)
//    return Status.RUNNING
//
//    Status.FAILED
//  }
//  override def copyTo(task: Task[Blackboard]): Task[Blackboard] = task
//}