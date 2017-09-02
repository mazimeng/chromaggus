package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.badlogic.gdx.math.{Circle, Vector2}
import com.workasintended.chromaggus.Factory.engine
import com.workasintended.chromaggus.component._
import com.workasintended.chromaggus.event.Events.UseAbility
import com.workasintended.chromaggus.event.{Event, EventHandler, Events}
import com.workasintended.chromaggus.job.MoveTo

import scala.collection.JavaConverters._

/**
  * Created by mazimeng on 7/30/17.
  */
class AbilitySystem() extends EntitySystem {
  val abilityComponent: ComponentMapper[AbilityComponent] = ComponentMapper.getFor(classOf[AbilityComponent])
  val attributeComponent: ComponentMapper[AttributeComponent] = ComponentMapper.getFor(classOf[AttributeComponent])
  val abilityCollectionComponent: ComponentMapper[AbilityCollectionComponent] = ComponentMapper.getFor(classOf[AbilityCollectionComponent])
  val movementComponent: ComponentMapper[PositionComponent] = ComponentMapper.getFor(classOf[PositionComponent])
  val deadComponent: ComponentMapper[DeadComponent] = ComponentMapper.getFor(classOf[DeadComponent])
  val useComponent: ComponentMapper[UseComponent] = ComponentMapper.getFor(classOf[UseComponent])
  val transformComponent: ComponentMapper[TransformComponent] = ComponentMapper.getFor(classOf[TransformComponent])

  val abilityUsed = new Event[Events.AbilityUsed]
  val useFamily: Family = Family.all(classOf[UseComponent]).get()

  val useAbilityHandler = new EventHandler[UseAbility] {
    override def handle(arg: UseAbility): Unit = {
      var ability: Option[Entity] = None
      if(arg.abilityName == AbilityComponent.ABILITY_DEFAULT) {
        ability = getFirstEquippedAbility(arg.user)
      }
      else {
        ability = getAbility(arg.abilityName, arg.user)
      }

      if(ability.isDefined) {
        val useComponent = new UseComponent(arg.user, arg.target, ability.get)
        arg.user.remove(classOf[UseComponent])
        arg.user.remove(classOf[JobComponent])
        arg.user.add(useComponent)
      }
    }
  }

  override def update(deltaTime: Float): Unit = {
    for(user <- getEngine.getEntitiesFor(useFamily).asScala) {
      updateUse(user, deltaTime)
    }
  }

  def updateUse(u: Entity, delta: Float): Unit = {
    val uc = useComponent.get(u)
    val target = uc.target
    val user = uc.user
    val ability = uc.ability
    val ac = abilityComponent.get(ability)
    val pos = movementComponent.get(target).position

    if (deadComponent.has(target) || !ac.isEquipped) {
      user.remove(classOf[UseComponent])
      return
    }

    if (uc.useState == UseComponent.STATE_PREPARING) {
      if (uc.useProgress >= ac.preparation) {
        uc.useProgress = 0f
        uc.useState = UseComponent.STATE_PREPARED

      }
      else {
        uc.useProgress += delta
      }
    }
    else if (uc.useState == UseComponent.STATE_PREPARED) {
      println("prepared")

      uc.useState = UseComponent.STATE_IDLE
      if (isInRange(ability, user, pos) && isReady(ability)) {
        useAbility(ability, user, target)
      }
    }
    else if (uc.useState == UseComponent.STATE_IDLE) {
      if (isInRange(ability, user, pos)) {
        if(isReady(ability)) {
          uc.useState = UseComponent.STATE_PREPARING
          println("preparing")
        }
      }
      else {
        updateFollow(user, target, delta)
      }
    }
  }

  def updateFollow(user: Entity, target: Entity, delta: Float): scala.Unit = {
    val speed = 32f
    val range2: Float = 32*32f

    val mc = movementComponent.get(user)
    val position = movementComponent.get(target).position

    if (mc == null || mc.position.dst2(position) <= range2) return

    val direction = new Vector2(position).sub(mc.position).nor()
    val velocity = direction.scl(delta * speed)

    mc.position.add(velocity)

    if (transformComponent.has(user)) {
      val tc = transformComponent.get(user)
      tc.position.set(mc.position)
    }
  }

  def useAbility(ability: Entity, user: Entity, target: Entity): Unit = {
    val abilityName = abilityComponent.get(ability).name
    if(abilityName == AbilityComponent.ABILITY_FIREBALL) {
      useFireball(user, target)
    }
    else if(abilityName == AbilityComponent.ABILITY_SIEGE) {

    }
  }

  def effect(ability: Entity, user: Entity, target: Entity, effect: Entity): Unit = {
    val dest = movementComponent.get(target).position
    val effectArea = new Circle(dest, 32f)
    val abc = abilityComponent.get(ability)
    val attr = attributeComponent.get(target)

    val effectMc = movementComponent.get(effect)
    if(effectArea.contains(effectMc.position)) {
      attr.health -= abc.damage  + abc.proficiency.toInt

      abc.proficiency += abc.proficiencyGrowth
      abilityUsed.fire(Events.AbilityUsed(ability))
    }
  }

  def makeProjectile(ability: Entity, user: Entity, target: Entity): Entity = {
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
    val mc = new PositionComponent(movementComponent.get(user).position)
    val jc = new JobComponent(moveTo)

    usable.add(ac)
    usable.add(tc)
    usable.add(mc)
    usable.add(jc)

    usable
  }

  def makeUsable(ability: Entity, user: Entity, target: Entity): Entity = {
    makeProjectile(ability, user, target)
  }

  def useDefault(user: Entity, target: Entity): Unit = {
    val ability = getFirstEquippedAbility(user)
    if(ability.isEmpty) return

    val ab = abilityComponent.get(ability.get)
    if(ab.name == AbilityComponent.ABILITY_FIREBALL) {
      useFireball(user, target)
    }
  }

  def useFireball(user: Entity, target: Entity): Unit = {
    val ability = getAbility(AbilityComponent.ABILITY_FIREBALL, user)

    if(ability.isEmpty) return

    val usable = new Entity()
    val dest = movementComponent.get(target).position
    val moveTo = new MoveTo(usable, dest)
    val ab = abilityComponent.get(ability.get)

    val ac = new ActorComponent(ab.actor)
    val tc = new TransformComponent(movementComponent.get(user).position)
    val mc = new PositionComponent(movementComponent.get(user).position)
    val jc = new JobComponent(moveTo)

    usable.add(ac)
    usable.add(tc)
    usable.add(mc)
    usable.add(jc)

    moveTo.speed = 256f
    moveTo.onDone = () => {
      val effectArea = new Circle(dest, 32f)
      val attr = attributeComponent.get(target)

      if(effectArea.contains(mc.position)) {
        attr.health -= ab.damage  + ab.proficiency.toInt

        ab.proficiency += ab.proficiencyGrowth
        abilityUsed.fire(Events.AbilityUsed(ability.get))
      }
      engine.removeEntity(usable)
    }

    ab.state = AbilityComponent.STATE_COOLINGDOWN

    getEngine.addEntity(usable)
  }

  def getAbility(abilityName: String, user: Entity): Option[Entity] = {
    if(!abilityCollectionComponent.has(user)) return None

    val acc = abilityCollectionComponent.get(user)
    acc.abilities.find(abilityComponent.get(_).name == abilityName)
  }

  def getFirstEquippedAbility(user: Entity): Option[Entity] = {
    if(!abilityCollectionComponent.has(user)) return None

    val acc = abilityCollectionComponent.get(user)
    acc.abilities.find(abilityComponent.get(_).isEquipped)
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

//  def getEquippedAbilities(user: Entity): Array[Entity] = {
//    val abilityCollection = abilityCollectionComponent.get(user)
//
//    val equippedAbilities: mutable.Set[Entity] = abilityCollection.abilities.filter(abilityComponent.get(_).isEquipped)
//
//    equippedAbilities
//  }
}
