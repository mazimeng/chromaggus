package com.workasintended.chromaggus.job

import com.badlogic.ashley.core.{ComponentMapper, Entity}
import com.badlogic.gdx.math.Vector2
import com.workasintended.chromaggus.Factory
import com.workasintended.chromaggus.component.MovementComponent

/**
  * Created by mazimeng on 7/26/17.
  */
class Use(val user: Entity, val target: Entity) extends Job {
  val movementComponent: ComponentMapper[MovementComponent] = ComponentMapper.getFor(classOf[MovementComponent])

  val follow = new Follow(user, target)
  val range2: Float = 64f*64f
  var progress = 0f

  val castingTime = 2f
  val IDLE: Int = 0
  val FOLLOWING: Int = 1
  val PREPARING: Int = 2
  val CASTED: Int = 3

  var state = FOLLOWING

  def castFireball(pos: Vector2): scala.Unit = {
    val fireball = Factory.makeFireball(user, pos)
    Factory.engine.addEntity(fireball)
  }

  override def update(delta: Float): scala.Unit = {
    super.update(delta)

    val dst2 = movementComponent.get(target).position.dst2(movementComponent.get(user).position)
    if(dst2 > range2 && state != PREPARING) {
      follow.update(delta)
    }
    else {
      if(progress >= castingTime) {
        if(dst2 <= range2) castFireball(movementComponent.get(target).position)

        progress = 0
        state = FOLLOWING
      }
      else {
        progress += delta
        if(state != PREPARING) {
          state = PREPARING
        }
      }
    }
  }
}
