package com.workasintended.chromaggus.system

import java.util.Observable

import com.badlogic.ashley.core._
import com.badlogic.ashley.systems.IteratingSystem
import com.workasintended.chromaggus.component.{CityComponent, FactionComponent}
import com.workasintended.chromaggus.event.Events.FactionIncomeChanged
import com.workasintended.chromaggus.event.{Event, Events}

class FactionSystem extends IteratingSystem(Family.all(classOf[FactionComponent]).get()) {
  val factionComponent: ComponentMapper[FactionComponent] = ComponentMapper.getFor(classOf[FactionComponent])
  val cityComponent: ComponentMapper[CityComponent] = ComponentMapper.getFor(classOf[CityComponent])
  val factionIncomeChanged = new Event[Events.FactionIncomeChanged]

  val tax = new Interval(1f, (faction: Entity) => {
    val fc = factionComponent.get(faction)
    for (elem <- fc.cities) {
      fc.gold += cityComponent.get(elem).income
    }

    factionIncomeChanged.fire(FactionIncomeChanged(faction))
  })

  val salary = new Interval(10f, (faction: Entity) => {
    val fc = factionComponent.get(faction)
    fc.gold -= 50 * fc.characters.size

    factionIncomeChanged.fire(FactionIncomeChanged(faction))
  })

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    tax.update(entity, deltaTime)
    salary.update(entity, deltaTime)
  }

  class Interval(val interval: Float, val callback: (Entity) => Unit, var elapsed: Float = 0f) {
    def update(faction: Entity, delta: Float): Unit = {
      elapsed += delta
      if(elapsed < interval) return
      elapsed = 0
      callback.apply(faction)
    }
  }
}
