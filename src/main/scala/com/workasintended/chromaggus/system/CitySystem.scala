package com.workasintended.chromaggus.system

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.workasintended.chromaggus.component.{CityComponent, FactionComponent}

/**
  * Created by mazimeng on 7/29/17.
  */
class CitySystem(family: Family) extends IteratingSystem(family) {
  def this() {
    this(Family.all(classOf[CityComponent]).get())
  }
  val incomeInterval = 1f
  var elapsed: Float = 0f
  val factionComponent: ComponentMapper[FactionComponent] = ComponentMapper.getFor(classOf[FactionComponent])
  val cityComponent: ComponentMapper[CityComponent] = ComponentMapper.getFor(classOf[CityComponent])

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    if(elapsed >= incomeInterval) {
      // collect income
      val faction = factionComponent.get(entity)
      val city = cityComponent.get(entity)
      faction.gold += city.income

      elapsed = 0f
    }
    else {
      elapsed += deltaTime
    }
  }
}
