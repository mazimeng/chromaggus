package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
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
  val deadComponent: ComponentMapper[DeadComponent] = ComponentMapper.getFor(classOf[DeadComponent])

  def this() {
    this(Family.all(classOf[AbilityComponent]).get())
  }


  override def addedToEngine(engine: Engine): Unit = {
    super.addedToEngine(engine)

    engine.addEntityListener(family, new EntityListener() {
      override def entityAdded(entity: Entity): Unit = {
        println("ability added")
      }

      override def entityRemoved(entity: Entity): Unit = {
        println("ability removed")
      }
    })
  }

  override def processEntity(entity: Entity, delta: Float): scala.Unit = {
    val ac = abilityComponent.get(entity)

    if (ac.state == AbilityComponent.STATE_PREPARING) {
      if (ac.progress >= ac.preparation) {
        println(s"casting is complete ${entity}")
        ac.state = AbilityComponent.STATE_COOLINGDOWN
        ac.progress = 0f
        if(ac.onUse.nonEmpty && !deadComponent.has(entity)) ac.onUse.get.apply()
        println(s"ability used ${entity}")
      }
      else {
        ac.progress += delta
      }
    }
    else if (ac.state == AbilityComponent.STATE_COOLINGDOWN) {
      if (ac.progress >= ac.cooldown) {
        ac.state = AbilityComponent.STATE_READY
        ac.progress = 0f
        println("cooled down")
      }
      else {
        ac.progress += delta
      }
    }
  }

  def use(ability: Entity, user: Entity, target: Entity): Boolean = {
    val ac = abilityComponent.get(ability)

    println(s"using ability: ${ac.state}")

    if (ac.state == AbilityComponent.STATE_READY) {
      println("start casting")
      ac.state = AbilityComponent.STATE_PREPARING

      if (ac.abilityType == AbilityComponent.TYPE_MISSLE) {
        ac.onUse = Some(() => {
          if(!deadComponent.has(target) && isInRange(ability, user, target)) {
            val missile = makeMissile(ability, user, target)
            println(s"missile added ${missile}")
            getEngine.addEntity(missile)
          }
        })
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
        if (elem != user && effect.contains(mc.get(elem).position)) {
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

  def isCoolingDown(ability: Entity): Boolean = {
    val ac = abilityComponent.get(ability)
    ac.state == AbilityComponent.STATE_COOLINGDOWN
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

  def isInRange(ability: Entity, user: Entity, target: Entity): Boolean = {
    val ac = abilityComponent.get(ability)
    val pos = movementComponent.get(target).position
    val dst2 = pos.dst2(movementComponent.get(user).position)

    dst2 <= ac.range2
  }
}
