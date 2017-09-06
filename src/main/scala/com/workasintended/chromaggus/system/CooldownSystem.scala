package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.{Circle, Vector2}
import com.workasintended.chromaggus.Factory.engine
import com.workasintended.chromaggus.component._
import com.workasintended.chromaggus.event.Events.UseAbility
import com.workasintended.chromaggus.event.{Event, EventHandler, Events}
import com.workasintended.chromaggus.job.MoveTo

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
