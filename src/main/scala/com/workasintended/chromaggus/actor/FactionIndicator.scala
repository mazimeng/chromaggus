package com.workasintended.chromaggus.actor

import com.badlogic.gdx.graphics.g2d.{Batch, BitmapFont}
import com.badlogic.gdx.scenes.scene2d.Actor
import com.workasintended.chromaggus.Factory
import com.workasintended.chromaggus.component.AttributeComponent

/**
  * Created by mazimeng on 7/29/17.
  */
class FactionIndicator(val factionName: String) extends Actor {
  val font: BitmapFont = Factory.bitmapFont
  override def draw(batch: Batch, parentAlpha: Float): scala.Unit = {
    font.draw(batch, factionName, getX(), getY())
    super.draw(batch, parentAlpha)
  }
}
