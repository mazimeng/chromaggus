package com.workasintended.chromaggus.system

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.workasintended.chromaggus.component.{BelongToFactionComponent, CharacterComponent, CityComponent, PositionComponent}

/**
  * Created by mazimeng on 7/29/17.
  */
class CitySystem(family: Family) extends IteratingSystem(family) {
  def this() {
    this(Family.all(classOf[CityComponent], classOf[BelongToFactionComponent], classOf[PositionComponent]).get())
  }

  val characterFamily = Family.all(classOf[CharacterComponent], classOf[PositionComponent], classOf[BelongToFactionComponent]).get()
  val movementComponent = ComponentMapper.getFor(classOf[PositionComponent])
  val belongToFactionComponent = ComponentMapper.getFor(classOf[BelongToFactionComponent])

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
//    val characters = getEngine.getEntitiesFor(characterFamily)
//    for(c <- characters) {
//
//    }
  }
}
