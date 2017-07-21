package com.workasintended.chromaggus.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.{Gdx, InputMultiplexer}

/**
  * Created by mazimeng on 7/21/17.
  */
class InputSystem(val stage: Stage) extends EntitySystem {
  val multiplexer = new InputMultiplexer()
  multiplexer.addProcessor(stage)
  Gdx.input.setInputProcessor(multiplexer)
  stage.addListener(new ActorGestureListener() {
    override def tap(event: InputEvent, x: Float, y: Float, count: Int, button: Int): scala.Unit = {
      val actor = stage.hit(x, y, true)
      println(s"tapped: ${event}, ${x}, ${y}, ${count}, ${button}, ${actor}")

    }
  })

}
