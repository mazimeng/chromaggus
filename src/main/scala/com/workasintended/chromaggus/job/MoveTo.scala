package com.workasintended.chromaggus.job

import com.badlogic.ashley.core.{ComponentMapper, Entity}
import com.badlogic.gdx.math.Vector2
import com.workasintended.chromaggus.component.{MovementComponent, TransformComponent}

/**
  * Created by mazimeng on 7/26/17.
  */
class MoveTo(val entity: Entity, val position: Vector2) extends Job {
  private val transformComponentMapper = ComponentMapper.getFor(classOf[TransformComponent])
  private val movementComponentMapper = ComponentMapper.getFor(classOf[MovementComponent])
  isDone = !movementComponentMapper.has(entity)

  override def update(delta: Float): scala.Unit = {
    val speed = 6.4f
    val range2 = 64f
    val mc = movementComponentMapper.get(entity)

    if (mc.position.dst2(position) <= range2) {
      isDone = true
      return
    }

    val direction = new Vector2(position).sub(mc.position).nor()
    val velocity = direction.scl(delta * speed)

    mc.position.add(velocity)

    if (transformComponentMapper.has(entity)) {
      val tc = transformComponentMapper.get(entity)
      tc.position.set(mc.position)
    }
  }
}
