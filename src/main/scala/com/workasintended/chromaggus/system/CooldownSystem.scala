package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.badlogic.ashley.systems.IteratingSystem
import com.workasintended.chromaggus.component._

/**
  * Created by mazimeng on 7/30/17.
  */
class CooldownSystem extends IteratingSystem(Family.all(classOf[AbilityComponent]).get()) {
  val abilityComponent: ComponentMapper[AbilityComponent] = ComponentMapper.getFor(classOf[AbilityComponent])

  override def processEntity(entity: Entity, delta: Float): scala.Unit = {
    val ac = abilityComponent.get(entity)

    if (ac.state == AbilityComponent.STATE_COOLINGDOWN) {
      if (ac.progress >= ac.cooldown) {
        ac.state = AbilityComponent.STATE_READY
        ac.progress = 0f
      }
      else {
        ac.progress += delta
      }
    }
  }
}
