package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.workasintended.chromaggus.component._

/**
  * Created by mazimeng on 7/25/17.
  */
class SelectionSystem() extends EntitySystem {
  private val transformComponentMapper = ComponentMapper.getFor(classOf[TransformComponent])
  private val actorComponentMapper = ComponentMapper.getFor(classOf[ActorComponent])
  private val selectedComponentMapper = ComponentMapper.getFor(classOf[SelectedComponent])
  private val selectableComponentMapper = ComponentMapper.getFor(classOf[SelectableComponent])
  private val selectedFamily: Family = Family.all(classOf[SelectedComponent], classOf[ActorComponent], classOf[SelectableComponent]).get()

  override def addedToEngine(engine: Engine): Unit = {
    super.addedToEngine(engine)

    getEngine.addEntityListener(selectedFamily, new EntityListener {
      override def entityAdded(entity: Entity): scala.Unit = {
        val gameActor = actorComponentMapper.get(entity).actor
        gameActor.addActor(selectableComponentMapper.get(entity).selectionActor)
        println(s"entity added in selectedFamily in SelectionSystem, children: ${gameActor.getChildren.size}")
      }

      override def entityRemoved(entity: Entity): scala.Unit = {
        println("entity removed in selectedFamily in SelectionSystem")
        val gameActor = actorComponentMapper.get(entity).actor
        gameActor.removeActor(selectableComponentMapper.get(entity).selectionActor, false)
        println(s"entity added in selectedFamily in SelectionSystem, children: ${gameActor.getChildren.size}")
      }
    })
  }
}
