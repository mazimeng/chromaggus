package com.workasintended.chromaggus

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Animation, Batch, TextureRegion}
import com.badlogic.gdx.scenes.scene2d.{Group, Touchable}

/**
  * Created by mazimeng on 7/22/17.
  */
class GameActor(val animation: Animation[TextureRegion]) extends Group {
  private var stateTime = 0f
  setTouchable(Touchable.enabled)

  def entity: Entity = {
    getUserObject.asInstanceOf[Entity]
  }

  def entity_=(entity: Entity): scala.Unit = {
    setUserObject(entity)
  }

  override def draw(batch: Batch, parentAlpha: Float): scala.Unit = {
    stateTime += Gdx.graphics.getDeltaTime

    val frame = animation.getKeyFrame(stateTime, true)
    batch.draw(frame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation())

    super.draw(batch, parentAlpha)
  }
}
