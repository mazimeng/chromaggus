package com.workasintended.chromaggus.job

import com.badlogic.ashley.core.{ComponentMapper, Entity}
import com.badlogic.gdx.math.Vector2
import com.workasintended.chromaggus.component.{MovementComponent, TransformComponent}

/**
  * Created by mazimeng on 7/26/17.
  */
class Follow(val entity: Entity, val target: Entity) extends Job {
  var speed = 32f
  var range2: Float = 32*32f
  private val transformComponentMapper = ComponentMapper.getFor(classOf[TransformComponent])
  private val movementComponentMapper = ComponentMapper.getFor(classOf[MovementComponent])

  override def update(delta: Float): scala.Unit = {
    super.update(delta)

    val mc = movementComponentMapper.get(entity)
    val position = movementComponentMapper.get(target).position

    if (mc == null || mc.position.dst2(position) <= range2) return

    val direction = new Vector2(position).sub(mc.position).nor()
    val velocity = direction.scl(delta * speed)

    mc.position.add(velocity)

    if (transformComponentMapper.has(entity)) {
      val tc = transformComponentMapper.get(entity)
      tc.position.set(mc.position)
    }
  }
}
