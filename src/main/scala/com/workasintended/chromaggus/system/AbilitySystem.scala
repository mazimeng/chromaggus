package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.{Circle, Vector2}
import com.workasintended.chromaggus.Factory.engine
import com.workasintended.chromaggus.component._
import com.workasintended.chromaggus.event.{Event, Events}
import com.workasintended.chromaggus.job.MoveTo

/**
  * Created by mazimeng on 7/30/17.
  */
class AbilitySystem(family: Family) extends IteratingSystem(family) {
  val abilityComponent: ComponentMapper[AbilityComponent] = ComponentMapper.getFor(classOf[AbilityComponent])
  val attributeComponent: ComponentMapper[AttributeComponent] = ComponentMapper.getFor(classOf[AttributeComponent])
  val abilityCollectionComponent: ComponentMapper[AbilityCollectionComponent] = ComponentMapper.getFor(classOf[AbilityCollectionComponent])
  val movementComponent: ComponentMapper[MovementComponent] = ComponentMapper.getFor(classOf[MovementComponent])
  val deadComponent: ComponentMapper[DeadComponent] = ComponentMapper.getFor(classOf[DeadComponent])
  val abilityUsed = new Event[Events.AbilityUsed]

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

  def effect(ability: Entity, user: Entity, target: Entity, effect: Entity): Unit = {
    val dest = movementComponent.get(target).position
    val effectArea = new Circle(dest, 32f)
    val abc = abilityComponent.get(ability)
    val attr = attributeComponent.get(target)

    val effectMc = movementComponent.get(effect)
    if(effectArea.contains(effectMc.position)) {
      attr.health -= abc.damage

      abc.proficiency += abc.proficiencyGrowth
      abilityUsed.fire(Events.AbilityUsed(ability))
    }
  }

  def makeUsable(ability: Entity, user: Entity, target: Entity): Entity = {
    val usable = new Entity()
    val dest = movementComponent.get(target).position
    val moveTo = new MoveTo(usable, dest)
    val ab = abilityComponent.get(ability)
    moveTo.speed = 256f
    moveTo.onDone = () => {
      effect(ability, user, target, usable)
      engine.removeEntity(usable)
    }

    val ac = new ActorComponent(ab.actor)
    val tc = new TransformComponent(movementComponent.get(user).position)
    val mc = new MovementComponent(movementComponent.get(user).position)
    val jc = new JobComponent(moveTo)

    usable.add(ac)
    usable.add(tc)
    usable.add(mc)
    usable.add(jc)

    usable
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
