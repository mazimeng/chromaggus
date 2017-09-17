package com.workasintended.chromaggus.system

import com.badlogic.ashley.core.{ComponentMapper, Entity, EntitySystem, Family}
import com.workasintended.chromaggus.component.{CharacterComponent, CityComponent, FactionComponent, PositionComponent}

class GlobalAiSystem extends EntitySystem {
  val factionFamily: Family = Family.all(classOf[FactionComponent]).get
  val cityFamily: Family = Family.all(classOf[CityComponent]).get
  val factionComponent: ComponentMapper[FactionComponent] = ComponentMapper.getFor(classOf[FactionComponent])
  val characterComponent: ComponentMapper[CharacterComponent] = ComponentMapper.getFor(classOf[CharacterComponent])
  val positionComponent: ComponentMapper[PositionComponent] = ComponentMapper.getFor(classOf[PositionComponent])

  override def update(deltaTime: Float): Unit = {

  }

  def makeTeam(us: Entity, them: Entity): Float = {
    val theirFaction = factionComponent.get(them)
    val ourFaction = factionComponent.get(them)

    for (ch <- ourFaction.characters) {
      val charPos = positionComponent.get(ch)
    }
    fc.cities
    val cities = getEngine.getEntitiesFor(cityFamily)
  }

  def evaluateThreat(): Unit = {

  }

  def evaluateStrength(): Unit = {

  }

}

