//package com.workasintended.chromaggus.target
//import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
//import com.badlogic.gdx.math.Circle
//import com.workasintended.chromaggus.component.MovementComponent
//
//import scala.collection.immutable.Seq
//
///**
//  * Created by mazimeng on 7/27/17.
//  */
//class SingleTargetWithinRange(val range: Circle) extends TargetSelection {
//  private val movementComponent = ComponentMapper.getFor(classOf[MovementComponent])
//  private val moveableFamily = Family.all(classOf[MovementComponent]).get()
//  override def targets: Seq[Entity] = {
//
//  }
//}
