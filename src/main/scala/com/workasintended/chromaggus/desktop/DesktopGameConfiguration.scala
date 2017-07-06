package com.workasintended.chromaggus.desktop

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.utils.viewport.{FillViewport, Viewport}
import com.workasintended.chromaggus.{GameConfiguration, Player, WorldStage}

/**
  * Created by mazimeng on 1/15/16.
  */
class DesktopGameConfiguration extends GameConfiguration {
  private var desktopInputHandler: DesktopInputHandler = _

  override def makeInputListener(worldStage: WorldStage, player: Player): EventListener = {
    this.desktopInputHandler = new DesktopInputHandler(worldStage, player)
    return desktopInputHandler
  }

  def makeWorldViewport: Viewport = {
    val (w, h) = (Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    val zoom = 1280 * 0.4f / w

    val cam = makeCamera()
    cam.zoom = zoom

    val viewport = new FillViewport(w, h)
    viewport.setCamera(cam);

    return viewport;
  }

  def makeGuiViewport: Viewport = {
    val cam = makeCamera()
    val (w, h) = (Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    val zoom = 1280 * 0.4f / w
    val viewport = new FillViewport(w * zoom, h * zoom)
    viewport.setCamera(cam)

    return viewport
  }
}
