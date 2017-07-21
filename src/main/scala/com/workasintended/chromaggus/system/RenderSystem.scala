package com.workasintended.chromaggus.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage

/**
  * Created by mazimeng on 7/20/17.
  */
class RenderSystem(val stage: Stage) extends EntitySystem {
  override def update(delta: Float): scala.Unit = {
    Gdx.gl.glClearColor(0.0f, 0.0f, 0f, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    stage.act(delta)
    stage.draw()
  }
}
