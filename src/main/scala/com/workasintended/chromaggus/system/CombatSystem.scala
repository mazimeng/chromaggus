package com.workasintended.chromaggus.system

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.workasintended.chromaggus.component.AttributeComponent

/**
  * Created by mazimeng on 7/29/17.
  */
class CombatSystem(family: Family) extends IteratingSystem(family) {
  val attributeComponent: ComponentMapper[AttributeComponent] = ComponentMapper.getFor(classOf[AttributeComponent])
  def this() {
    this(Family.all(classOf[AttributeComponent]).get())
  }

  override def processEntity(entity: Entity, deltaTime: Float): scala.Unit = {
    val com = attributeComponent.get(entity)

  }
}
