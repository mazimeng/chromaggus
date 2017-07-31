package com.workasintended.chromaggus.job

import com.badlogic.ashley.core.{ComponentMapper, Engine, Entity}
import com.badlogic.gdx.math.Vector2
import com.workasintended.chromaggus.Factory
import com.workasintended.chromaggus.component.{AbilityComponent, DeadComponent, EffectComponent, MovementComponent}
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
  val IDLE: Int = 0
  val FOLLOWING: Int = 1
  val PREPARING: Int = 2
  val CASTED: Int = 3

  var state = FOLLOWING

  override def update(delta: Float): scala.Unit = {
    super.update(delta)

    val pos = movementComponent.get(target).position

    if(!abilitySystem.isInRange(ability, user, pos) && !abilitySystem.isPreparing(ability)) {
      follow.update(delta)
    }
    else {
      abilitySystem.use(ability, user, target)
    }
  }
}
