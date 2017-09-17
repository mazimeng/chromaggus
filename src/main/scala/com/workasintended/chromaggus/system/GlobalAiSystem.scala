package com.workasintended.chromaggus.system

import com.badlogic.ashley.core.{ComponentMapper, Entity, EntitySystem, Family}
import com.workasintended.chromaggus.component.{CharacterComponent, CityComponent, FactionComponent, PositionComponent}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class GlobalAiSystem extends EntitySystem {
  val factionFamily: Family = Family.all(classOf[FactionComponent]).get
  val cityFamily: Family = Family.all(classOf[CityComponent]).get
  val factionComponent: ComponentMapper[FactionComponent] = ComponentMapper.getFor(classOf[FactionComponent])
  val characterComponent: ComponentMapper[CharacterComponent] = ComponentMapper.getFor(classOf[CharacterComponent])
  val positionComponent: ComponentMapper[PositionComponent] = ComponentMapper.getFor(classOf[PositionComponent])

  class Distance(val distance: Float, val ourCharacter: Entity, val theirCity: Entity)

  override def update(deltaTime: Float): Unit = {

  }

  def makeTeam(us: Entity, them: Entity): Float = {
    val theirFaction = factionComponent.get(them)
    val ourFaction = factionComponent.get(them)

    val distances: ListBuffer[Distance] = ListBuffer()
    val targetCities = mutable.Map[Entity, ListBuffer[Entity]]()
    for (ourChar <- ourFaction.characters) {
      for (theirCity <- theirFaction.cities) {

        val cityPos = positionComponent.get(theirCity)
        val charPos = positionComponent.get(ourChar)

        val dst2 = cityPos.position.dst2(charPos.position)
        if(dst2 <= 128*128) {
          val lb = targetCities.getOrElseUpdate(theirCity, ListBuffer())
          lb += ourChar
        }
      }
    }

    val teamCandidates = mutable.Map[Entity, ListBuffer[Entity]]()
    for (elem <- targetCities) {
      val (theirCity, ourChars) = elem
      var theirCharCount = 0
      for (theirChar <- theirFaction.characters) {
        val cityPos = positionComponent.get(theirCity)
        val charPos = positionComponent.get(theirChar)
        val dst2 = cityPos.position.dst2(charPos.position)
        if(dst2 <= 128*128) {
          theirCharCount += 1
        }
      }

      if((ourChars.length - theirCharCount) > 0) {
        teamCandidates.put(theirCity, ourChars)
      }
    }

    0f
  }

  def evaluateThreat(): Unit = {

  }

  def evaluateStrength(): Unit = {

  }

}

