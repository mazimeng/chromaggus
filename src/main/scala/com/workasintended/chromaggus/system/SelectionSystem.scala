package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.badlogic.ashley.systems.IteratingSystem
import com.workasintended.chromaggus.component.{SelectedComponent, SelectionComponent, TransformComponent}

/**
  * Created by mazimeng on 7/25/17.
  */
class SelectionSystem(family: Family) extends IteratingSystem(family) {
  private val transformComponentMapper = ComponentMapper.getFor(classOf[TransformComponent])
  private val selectionComponentMapper = ComponentMapper.getFor(classOf[SelectionComponent])

  def this() {
    this(Family.all(classOf[SelectionComponent], classOf[TransformComponent]).get())
  }

  override def processEntity(entity: Entity, v: Float): Unit = {
    val tc = transformComponentMapper.get(entity)
    val sc = selectionComponentMapper.get(entity)
    val targetTc = transformComponentMapper.get(sc.target)

    tc.position.set(targetTc.position)
  }

  override def addedToEngine(engine: Engine): Unit = {
    super.addedToEngine(engine)

    engine.addEntityListener(getFamily, new EntityListener() {
      override def entityAdded(entity: Entity): scala.Unit = {
        val selection = selectionComponentMapper.get(entity)
        selection.target.add(new SelectedComponent())
      }

      override def entityRemoved(entity: Entity): scala.Unit = {
        val selectionComponent = selectionComponentMapper.get(entity)
        selectionComponent.target.remove(classOf[SelectedComponent])
      }
    })
  }
}
