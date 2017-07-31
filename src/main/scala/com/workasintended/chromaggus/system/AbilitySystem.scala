package com.workasintended.chromaggus.system

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import com.workasintended.chromaggus.component.{AbilityComponent, EffectComponent, MovementComponent}

/**
  * Created by mazimeng on 7/30/17.
  */
class AbilitySystem(family: Family) extends IteratingSystem(family) {
  val abilityComponent: ComponentMapper[AbilityComponent] = ComponentMapper.getFor(classOf[AbilityComponent])
  val movementComponent: ComponentMapper[MovementComponent] = ComponentMapper.getFor(classOf[MovementComponent])

  def this() {
    this(Family.all(classOf[EffectComponent]).get())
  }

  override def processEntity(entity: Entity, delta: Float): scala.Unit = {
    val ac = abilityComponent.get(entity)

    if(ac.state == AbilityComponent.STATE_PREPARING) {
      if(ac.progress >= ac.preparation) {
        ac.state = AbilityComponent.STATE_COOLINGDOWN
        ac.progress = 0f

        getEngine.addEntity(ac.effect)
      }
      else {
        ac.progress += delta
      }
    }
    else if(ac.state == AbilityComponent.STATE_COOLINGDOWN) {
      if(ac.progress >= ac.preparation) {
        ac.state = AbilityComponent.STATE_READY
        ac.progress = 0f
      }
      else {
        ac.progress += delta
      }
    }
  }

  def use(ability: Entity, user: Entity, target: Entity): Boolean = {
    val ac = abilityComponent.get(ability)

    if(ac.state == AbilityComponent.STATE_READY) {
      ac.state = AbilityComponent.STATE_PREPARING
      getEngine.addEntity(ability)
      true
    }
    else false
  }

  def isReady(ability: Entity): Boolean = {
    val ac = abilityComponent.get(ability)
    ac.state == AbilityComponent.STATE_READY
  }

  def isPreparing(ability: Entity): Boolean = {
    val ac = abilityComponent.get(ability)
    ac.state == AbilityComponent.STATE_PREPARING
  }

  def isInRange(ability: Entity, user: Entity, pos: Vector2): Boolean = {
    val ac = abilityComponent.get(ability)
    val dst2 = pos.dst2(movementComponent.get(user).position)

    dst2 <= ac.range2
  }
}
