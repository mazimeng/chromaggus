package com.workasintended.chromaggus.actor

import com.badlogic.gdx.graphics.g2d.{Batch, BitmapFont}
import com.badlogic.gdx.scenes.scene2d.Actor
import com.workasintended.chromaggus.Factory
import com.workasintended.chromaggus.component.AttributeComponent

/**
  * Created by mazimeng on 7/29/17.
  */
class UsingAbilityActor(val attributeComponent: AttributeComponent) extends Actor {
  val font: BitmapFont = Factory.bitmapFont
  override def draw(batch: Batch, parentAlpha: Float): scala.Unit = {
    font.draw(batch, s"${attributeComponent.health}", getX(), getY())
    super.draw(batch, parentAlpha)
  }
}
