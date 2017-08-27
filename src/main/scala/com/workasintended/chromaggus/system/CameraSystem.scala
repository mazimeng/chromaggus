package com.workasintended.chromaggus.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
/**
  * Created by mazimeng on 7/29/17.
  */
class CameraSystem(val stage: Stage) extends EntitySystem {
  val camera: OrthographicCamera = stage.getCamera().asInstanceOf[OrthographicCamera]

  def moveBy(deltaX: Float, deltaY: Float): Unit = {
    camera.translate(-0.5f * deltaX, -0.5f * deltaY)

    // sprites look out of sync without camera updating
    camera.update()
  }
}
