//package com.workasintended.chromaggus
//
//import com.badlogic.ashley.core.Entity
//import com.badlogic.gdx.Gdx
//import com.badlogic.gdx.graphics.g2d.Batch
//import com.badlogic.gdx.maps.MapLayer
//import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
//import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
//import com.badlogic.gdx.scenes.scene2d.{Group, Touchable}
//
//import scala.collection.JavaConverters._
//
///**
//  * Created by mazimeng on 8/2/17.
//  */
//class WorldActor(worldRenderer: OrthogonalTiledMapRenderer) extends Group {
//  var stateTime = 0f
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
//  override def draw(batch: Batch, parentAlpha: Float): scala.Unit = {
//
//    val i: java.util.Iterator[MapLayer] = worldRenderer.getMap().getLayers().iterator()
//
//    for (layer: MapLayer <- worldRenderer.getMap().getLayers().asScala) {
//      if(layer.isVisible()) {
//        if(layer.isInstanceOf[TiledMapTileLayer]) {
//          this.renderTileLayer((TiledMapTileLayer)layer);
//        } else if(layer instanceof TiledMapImageLayer) {
//          this.renderImageLayer((TiledMapImageLayer)layer);
//        } else {
//          this.renderObjects(layer);
//        }
//      }
//    }
//    super.draw(batch, parentAlpha)
//  }
//}