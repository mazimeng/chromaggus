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

  override def update(delta: Float): scala.Unit = {
    super.update(delta)

    val pos = movementComponent.get(target).position

    if(deadComponent.has(target)) {
      println("complete on death")
      complete()
      return
    }

    if (abilitySystem.isPreparing(ability)) {

    }
    else {
      if (abilitySystem.isInRange(ability, user, pos)) {
        if(!abilitySystem.isCoolingDown(ability)) {
          abilitySystem.use(ability, user, target)
        }
//        complete()
      }
      else {
        follow.update(delta)
      }
    }
  }
}
