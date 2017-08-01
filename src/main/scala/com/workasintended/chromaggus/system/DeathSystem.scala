package com.workasintended.chromaggus.system

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.workasintended.chromaggus.component.{AttributeComponent, DeadComponent}

/**
  * Created by mazimeng on 7/29/17.
  */
class DeathSystem(family: Family) extends IteratingSystem(family) {
  val deadComponent: ComponentMapper[DeadComponent] = ComponentMapper.getFor(classOf[DeadComponent])
  val attributeComponent: ComponentMapper[AttributeComponent] = ComponentMapper.getFor(classOf[AttributeComponent])

  def this() {
    this(Family.all(classOf[AttributeComponent]).get())
  }
  override def processEntity(entity: Entity, deltaTime: Float): scala.Unit = {
    if(attributeComponent.get(entity).health <= 0 && !deadComponent.has(entity)) {
      println("a character is dead")
      entity.add(new DeadComponent())
    }
    else if(attributeComponent.get(entity).health > 0 && deadComponent.has(entity)){
      entity.remove(classOf[DeadComponent])
    }
  }
}
