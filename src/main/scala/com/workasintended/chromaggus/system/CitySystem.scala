package com.workasintended.chromaggus.system

import java.util.Observable

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.workasintended.chromaggus.component.{CityComponent, FactionComponent}

/**
  * Created by mazimeng on 7/29/17.
  */
class CitySystem(family: Family) extends IteratingSystem(family) {
  val factionIncomeChanged = new Observable()

  def this() {
    this(Family.all(classOf[CityComponent]).get())
  }

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
  }
}
