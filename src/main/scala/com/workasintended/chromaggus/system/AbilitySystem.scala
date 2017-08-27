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
  val abilityCollectionComponent: ComponentMapper[AbilityCollectionComponent] = ComponentMapper.getFor(classOf[AbilityCollectionComponent])
  val movementComponent: ComponentMapper[MovementComponent] = ComponentMapper.getFor(classOf[MovementComponent])
  val deadComponent: ComponentMapper[DeadComponent] = ComponentMapper.getFor(classOf[DeadComponent])

  def this() {
    this(Family.all(classOf[AbilityComponent]).get())
  }

//  override def addedToEngine(engine: Engine): Unit = {
//    super.addedToEngine(engine)
//
//    engine.addEntityListener(family, new EntityListener() {
//      override def entityAdded(entity: Entity): Unit = {
//        println("ability added")
//      }
//
//      override def entityRemoved(entity: Entity): Unit = {
//        println("ability removed")
//      }
//    })
//  }

  override def processEntity(entity: Entity, delta: Float): scala.Unit = {
    val ac = abilityComponent.get(entity)

    if (ac.state == AbilityComponent.STATE_COOLINGDOWN) {
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

    val missile = makeUsable(ability, user, target)
    getEngine.addEntity(missile)
    ac.state = AbilityComponent.STATE_COOLINGDOWN

    true
  }

  def makeUsable(ability: Entity, user: Entity, target: Entity): Entity = {
    val mc: ComponentMapper[MovementComponent] = ComponentMapper.getFor(classOf[MovementComponent])
    val ac: ComponentMapper[AbilityComponent] = ComponentMapper.getFor(classOf[AbilityComponent])
    val ab = ac.get(ability)
    val effect = new Entity()
    val dest = mc.get(target).position
    val moveTo = new MoveTo(effect, dest)
    val effectArea = new Circle(dest, 32f)
    val damage = ab.damage
    moveTo.speed = 256f
    moveTo.onDone = () => {
      val targetFamily: Family = Family.all(classOf[AttributeComponent]).get()
      val ac: ComponentMapper[AttributeComponent] = ComponentMapper.getFor(classOf[AttributeComponent])
      val entities = engine.getEntitiesFor(targetFamily).asScala
      for (elem <- entities) {
        if (elem != user && effectArea.contains(mc.get(elem).position)) {
          ac.get(elem).health -= damage
        }
      }
      engine.removeEntity(effect)
    }

    val actorComponent = new ActorComponent(ab.actor)
    val transformComponent = new TransformComponent(mc.get(user).position)
    val movementComponent = new MovementComponent(mc.get(user).position)
    val jobComponent = new JobComponent(moveTo)

    effect.add(actorComponent)
    effect.add(transformComponent)
    effect.add(movementComponent)
    effect.add(jobComponent)

    effect
  }

  def isCoolingDown(ability: Entity): Boolean = {
    val ac = abilityComponent.get(ability)
    ac.state == AbilityComponent.STATE_COOLINGDOWN
  }

  def isReady(ability: Entity): Boolean = {
    val ac = abilityComponent.get(ability)
    ac.state == AbilityComponent.STATE_READY
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

  def getEquippedAbilities(user: Entity): Array[Entity] = {
    val abilityCollection = abilityCollectionComponent.get(user)

    val equippedAbilities: Array[Entity] = abilityCollection.abilities.filter(ent => ent != null && abilityComponent.get(ent).isEquipped)

    equippedAbilities
  }
}
