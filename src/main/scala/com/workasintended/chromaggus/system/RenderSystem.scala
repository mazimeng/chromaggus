package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.workasintended.chromaggus.component.{CharacterComponent, ActorComponent}

/**
  * Created by mazimeng on 7/20/17.
  */
class RenderSystem(val stage: Stage) extends EntitySystem {
  val family: Family = Family.one(classOf[ActorComponent]).get()
  private val renderableComponentMapper = ComponentMapper.getFor(classOf[ActorComponent])


  override def addedToEngine(engine: Engine) {
    engine.addEntityListener(family, new EntityListener() {
      override def entityAdded(entity: Entity): scala.Unit = {
        val component = renderableComponentMapper.get(entity)
        stage.addActor(component)
      }

      override def entityRemoved(entity: Entity): scala.Unit = {
      }
    })
  }

  override def update(delta: Float): scala.Unit = {
    Gdx.gl.glClearColor(0.0f, 0.0f, 0f, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    stage.act(delta)
    stage.draw()
  }
}
