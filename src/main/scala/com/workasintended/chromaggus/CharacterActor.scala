//package com.workasintended.chromaggus
//
//import com.badlogic.ashley.core.Entity
//import com.badlogic.gdx.Gdx
//import com.badlogic.gdx.graphics.g2d.{Animation, Batch, TextureRegion}
//import com.badlogic.gdx.scenes.scene2d.{Actor, Touchable}
//
///**
//  * Created by mazimeng on 7/22/17.
//  */
//class CharacterActor(val animation: Animation[TextureRegion]) extends Actor {
//  var selected: Boolean = false
//  private var stateTime = 0f
//  setTouchable(Touchable.enabled)
//
//  def entity: Entity = {
//    getUserObject.asInstanceOf[Entity]
//  }
//
//  def entity_=(entity: Entity): scala.Unit = {
//    setUserObject(entity)
//  }
//
//
//  override def draw(batch: Batch, parentAlpha: Float): scala.Unit = {
//    stateTime += Gdx.graphics.getDeltaTime
//
//    renderCharacter(batch, parentAlpha)
//    renderSelection(batch, parentAlpha)
//  }
//
//  private def renderCharacter(batch: Batch, parentAlpha: Float): scala.Unit = {
//    val frame = animation.getKeyFrame(stateTime, true)
//    batch.draw(frame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation())
//  }
//
//  private def renderSelection(batch: Batch, parentAlpha: Float): scala.Unit = {
//    if(!selected) return
//
//    val frame = selection.getKeyFrame(stateTime, true)
//    batch.draw(frame, getX, getY, getOriginX, getOriginY, getWidth, getHeight, getScaleX, getScaleY, getRotation)
//  }
//}
