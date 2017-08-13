package com.workasintended.chromaggus.job

import com.badlogic.ashley.core.{ComponentMapper, Entity}
import com.workasintended.chromaggus.component.{AbilityComponent, DeadComponent, MovementComponent}
import com.workasintended.chromaggus.system.AbilitySystem

/**
  * Created by mazimeng on 7/26/17.
  */
class Use(val user: Entity,
          val target: Entity,
          val ability: Entity,
          val abilitySystem: AbilitySystem) extends Job {
  val movementComponent: ComponentMapper[MovementComponent] = ComponentMapper.getFor(classOf[MovementComponent])
  val deadComponent: ComponentMapper[DeadComponent] = ComponentMapper.getFor(classOf[DeadComponent])
  val abilityComponent: ComponentMapper[AbilityComponent] = ComponentMapper.getFor(classOf[AbilityComponent])

  val follow = new Follow(user, target)

  val STATE_IDLE: Int = 0
  val STATE_PREPARING: Int = 1
  val STATE_PREPARED: Int = 2
  var useProgress = 0f
  var useState = STATE_IDLE

  def use(): scala.Unit = {
    //    if (abilitySystem.isInRange(ability, user, pos)) {
    //      if(!abilitySystem.isCoolingDown(ability)) {
    //        abilitySystem.use(ability, user, target)
    //        useState = USE_STATE_IDLE
    //      }
    //    }
    //    else {
    //      follow.update(delta)
    //    }
  }

  override def update(delta: Float): scala.Unit = {
    super.update(delta)

    val pos = movementComponent.get(target).position

    if (deadComponent.has(target)) {
      println("complete on death")
      complete()
      return
    }

    val ac = abilityComponent.get(ability)

    if (useState == STATE_PREPARING) {
      if (useProgress >= ac.preparation) {
        useProgress = 0f
        useState = STATE_PREPARED

      }
      else {
        useProgress += delta
      }
    }
    else if (useState == STATE_PREPARED) {
      println("prepared")

      useState = STATE_IDLE
      if (abilitySystem.isInRange(ability, user, pos) && abilitySystem.isReady(ability)) {
        abilitySystem.use(ability, user, target)
      }
    }
    else if (useState == STATE_IDLE) {
      if (abilitySystem.isInRange(ability, user, pos)) {
        if(abilitySystem.isReady(ability)) {
          useState = STATE_PREPARING
          println("preparing")
        }
      }
      else {
        follow.update(delta)
      }
    }
  }
}
