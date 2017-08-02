package com.workasintended.chromaggus.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer

/**
  * Created by mazimeng on 8/2/17.
  */
class WorldRenderingSystem(worldRenderer: OrthogonalTiledMapRenderer) extends EntitySystem {
  override def update(deltaTime: Float): Unit = {
    OrthographicCamera camera = (OrthographicCamera) getCamera();
    getTiledMapRenderer().setView(camera);
    worldRenderer.render()
  }
}
