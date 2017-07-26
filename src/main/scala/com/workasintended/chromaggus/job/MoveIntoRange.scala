package com.workasintended.chromaggus.job

import com.badlogic.ashley.core.{ComponentMapper, Entity}
import com.badlogic.gdx.math.{Circle, Vector2}
import com.workasintended.chromaggus.component.{MovementComponent, TransformComponent}

/**
  * Created by mazimeng on 7/26/17.
  */
class MoveIntoRange(val entity: Entity, val range: Circle) extends Job {
  var speed = 32f
  private val transformComponentMapper = ComponentMapper.getFor(classOf[TransformComponent])
  private val movementComponentMapper = ComponentMapper.getFor(classOf[MovementComponent])

  override def update(delta: Float): scala.Unit = {
    super.update(delta)
    val mc = movementComponentMapper.get(entity)

    if (mc == null || range.contains(mc.position)) {
      complete()
      return
    }

    val direction = new Vector2(range.x, range.y).sub(mc.position).nor()
    val velocity = direction.scl(delta * speed)

    mc.position.add(velocity)

    if (transformComponentMapper.has(entity)) {
      val tc = transformComponentMapper.get(entity)
      tc.position.set(mc.position)
    }
  }
}
