package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.badlogic.gdx.scenes.scene2d.Stage
import com.workasintended.chromaggus.component.{ActorComponent, SelectableComponent, SelectedComponent, TransformComponent}

/**
  * Created by mazimeng on 7/30/17.
  */
class UiSystem(val ui: Stage) extends EntitySystem {
  private val actorComponentMapper = ComponentMapper.getFor(classOf[ActorComponent])
  private val selectableComponentMapper = ComponentMapper.getFor(classOf[SelectableComponent])
  private val selectedFamily: Family = Family.all(classOf[SelectedComponent], classOf[ActorComponent], classOf[SelectableComponent]).get()

  override def addedToEngine(engine: Engine): Unit = {
    super.addedToEngine(engine)

    getEngine.addEntityListener(selectedFamily, new EntityListener {
      override def entityAdded(entity: Entity): scala.Unit = {
        val gameActor = actorComponentMapper.get(entity).actor
        gameActor.addActor(selec tableComponentMapper.get(entity).selectionActor)
      }

      override def entityRemoved(entity: Entity): scala.Unit = {
        val gameActor = actorComponentMapper.get(entity).actor
        gameActor.removeActor(selectableComponentMapper.get(entity).selectionActor, false)
      }
    })
  }
}
