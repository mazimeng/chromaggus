//package com.workasintended.chromaggus.system
//
//import com.badlogic.ashley.core.{Engine, Entity, EntityListener, EntitySystem}
//
///**
//  * Created by mazimeng on 7/30/17.
//  */
//class UiSystem extends EntitySystem {
//  override def addedToEngine(engine: Engine): scala.Unit = {
//    super.addedToEngine(engine)
//    engine.addEntityListener(family, new EntityListener() {
//      override def entityAdded(entity: Entity): scala.Unit = {
//        val component = behaviorComponent.get(entity)
//        component.behaviorTree.reset()
//      }
//
//      override def entityRemoved(entity: Entity): scala.Unit = {
//      }
//    })
//  }
//}
