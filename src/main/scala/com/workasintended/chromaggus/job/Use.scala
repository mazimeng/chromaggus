package com.workasintended.chromaggus.job

import com.badlogic.ashley.core.{ComponentMapper, Entity}
import com.badlogic.gdx.math.Circle
import com.workasintended.chromaggus.component.MovementComponent

/**
  * Created by mazimeng on 7/26/17.
  */
class Use(val user: Entity, val target: Entity) extends Job{
  val movementComponementMapper: ComponentMapper[MovementComponent] = ComponentMapper.getFor(classOf[MovementComponent])

  val move = new MoveIntoRange(user, new Circle(movementComponementMapper.get(target).position, 32f))
  override def update(delta: Float): scala.Unit = {
    super.update(delta)
  }
}
