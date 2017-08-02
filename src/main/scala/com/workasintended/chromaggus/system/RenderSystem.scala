package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.workasintended.chromaggus.component.{ActorComponent, TransformComponent}

/**
  * Created by mazimeng on 7/20/17.
  */
class RenderSystem(val stage: Stage, val ui: Stage, val worldRenderer: OrthogonalTiledMapRenderer, val family: Family) extends IteratingSystem(family) {
  def this(stage: Stage, ui: Stage, worldRenderer: OrthogonalTiledMapRenderer) {
    this(stage, ui, worldRenderer, Family.all(classOf[ActorComponent], classOf[TransformComponent]).get())
  }
  private val actorComponentMapper = ComponentMapper.getFor(classOf[ActorComponent])
  private val transformComponentMapper = ComponentMapper.getFor(classOf[TransformComponent])

  override def addedToEngine(engine: Engine) {
    super.addedToEngine(engine)

    engine.addEntityListener(family, new EntityListener() {
      override def entityAdded(entity: Entity): scala.Unit = {
        val component = actorComponentMapper.get(entity)
        stage.addActor(component.actor)
      }

      override def entityRemoved(entity: Entity): scala.Unit = {
        val component = actorComponentMapper.get(entity)
//        stage.getActors.removeValue(component.actor, false)
        component.actor.remove()
      }
    })
  }

  override def update(delta: Float): scala.Unit = {
    super.update(delta)

    Gdx.gl.glClearColor(0.0f, 0.0f, 0f, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)


    stage.act(delta)
    ui.act(delta)

    worldRenderer.render()
    stage.draw()
    ui.draw()
  }

  override def processEntity(entity: Entity, delta: Float): scala.Unit = {
    val ac = actorComponentMapper.get(entity)
    val tc = transformComponentMapper.get(entity)

    ac.actor.setPosition(tc.position.x, tc.position.y)
  }
}
