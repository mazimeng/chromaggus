package com.workasintended.chromaggus.job

import com.badlogic.ashley.core.{ComponentMapper, Entity}
import com.badlogic.gdx.math.Circle
import com.workasintended.chromaggus.component.MovementComponent

/**
  * Created by mazimeng on 7/26/17.
  */
class Use(val user: Entity, val target: Entity) extends Job {
  val movementComponent: ComponentMapper[MovementComponent] = ComponentMapper.getFor(classOf[MovementComponent])

  val follow = new Follow(user, target)
  val range2: Float = 64f*64f
  var casting = false
  var progress = 0f
  var state = 0

  override def update(delta: Float): scala.Unit = {
    super.update(delta)

    val dst2 = movementComponent.get(target).position.dst2(movementComponent.get(user).position)
    if(dst2 > range2 && !casting) {
      if(state != 2) {
        println("start following")
        state = 2
      }

      follow.update(delta)
    }
    else {
      if(progress > 2) {
        casting = false
        progress = 0
        println("start casting")
      }
      else {
        casting = true
        progress += delta

        if(state != 1) {
          println("start casting")
          state = 1
        }
      }
    }
  }
}
