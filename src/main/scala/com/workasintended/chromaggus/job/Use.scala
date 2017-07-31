package com.workasintended.chromaggus.job

import com.badlogic.ashley.core.{ComponentMapper, Engine, Entity}
import com.badlogic.gdx.math.Vector2
import com.workasintended.chromaggus.Factory
import com.workasintended.chromaggus.component.{AbilityComponent, DeadComponent, EffectComponent, MovementComponent}

/**
  * Created by mazimeng on 7/26/17.
  */
class Use(val user: Entity,
          val target: Entity,
          val ability: Entity,
          val engine: Engine) extends Job {
  val movementComponent: ComponentMapper[MovementComponent] = ComponentMapper.getFor(classOf[MovementComponent])
  val deadComponent: ComponentMapper[DeadComponent] = ComponentMapper.getFor(classOf[DeadComponent])
  val abilityComponent: ComponentMapper[AbilityComponent] = ComponentMapper.getFor(classOf[AbilityComponent])

  val follow = new Follow(user, target)
//  val range2: Float = 64f*64f
//  var progress = 0f

//  val castingTime = 2f
  val IDLE: Int = 0
  val FOLLOWING: Int = 1
  val PREPARING: Int = 2
  val CASTED: Int = 3

  var state = FOLLOWING

  def use(pos: Vector2): scala.Unit = {
    val fireball = Factory.makeFireball(user, pos)
    Factory.engine.addEntity(fireball)
  }

  override def update(delta: Float): scala.Unit = {
    super.update(delta)

    val dst2 = movementComponent.get(target).position.dst2(movementComponent.get(user).position)
    val ec = effectComponent.get(effect)

    if(dst2 > ec.range2 && ec.state != EffectComponent.STATE_PREPARING) {
      follow.update(delta)
    }
    else {
      if(ec.progress >= ec.preparation) {
        if(deadComponent.has(target)) {
          complete()
        }
        else {
          if(dst2 <= ec.preparation) use(movementComponent.get(target).position)

          state = FOLLOWING
        }
        ec.progress = 0f
      }
      else {
        ec.progress += delta
        if(state != PREPARING) {
          state = PREPARING
        }
      }
    }
  }
}
