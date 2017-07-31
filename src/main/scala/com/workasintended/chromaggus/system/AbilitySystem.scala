package com.workasintended.chromaggus.system

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.{Circle, Vector2}
import com.workasintended.chromaggus.Factory.engine
import com.workasintended.chromaggus.component._
import com.workasintended.chromaggus.job.MoveTo

import scala.collection.JavaConverters._

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

      if(ac.abilityType == AbilityComponent.TYPE_MISSLE) {
        val missile = makeMissile(ability, user, target)
        getEngine.addEntity(missile)
        println("fireball cast")
      }
      true
    }
    else false
  }

  def makeMissile(ability: Entity, user: Entity, target: Entity): Entity = {
    val mc: ComponentMapper[MovementComponent] = ComponentMapper.getFor(classOf[MovementComponent])
    val ac: ComponentMapper[AbilityComponent] = ComponentMapper.getFor(classOf[AbilityComponent])
    val ab = ac.get(ability)
    val entity = new Entity()
    val dest = mc.get(target).position
    val moveTo = new MoveTo(entity, dest)
    val effect = new Circle(dest, 32f)
    val damage = ab.damage
    moveTo.speed = 256f
    moveTo.onDone = () => {
      val targetFamily: Family = Family.all(classOf[AttributeComponent]).get()
      val ac: ComponentMapper[AttributeComponent] = ComponentMapper.getFor(classOf[AttributeComponent])
      val entities = engine.getEntitiesFor(targetFamily).asScala
      for (elem <- entities) {
        if(elem != user && effect.contains(mc.get(elem).position)) {
          ac.get(elem).health -= damage
        }
      }
      engine.removeEntity(entity)
    }

    val actorComponent = new ActorComponent(ab.actor)
    val transformComponent = new TransformComponent(mc.get(user).position)
    val movementComponent = new MovementComponent(mc.get(user).position)
    val jobComponent = new JobComponent(moveTo)

    entity.add(actorComponent)
    entity.add(transformComponent)
    entity.add(movementComponent)
    entity.add(jobComponent)

    entity
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
