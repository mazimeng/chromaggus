package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.workasintended.chromaggus.GameActor
import com.workasintended.chromaggus.component._
import com.workasintended.chromaggus.event.Events.TargetRequired
import com.workasintended.chromaggus.event.{Event, EventHandler, Events}

import scala.collection.JavaConverters._

/**
  * Created by mazimeng on 7/21/17.
  */
class ControlSystem(val stage: Stage) extends EntitySystem {
  private val selectedComponentMapper = ComponentMapper.getFor(classOf[SelectedComponent])
  private val acmm = ComponentMapper.getFor(classOf[AbilityCollectionComponent])
  private val acm = ComponentMapper.getFor(classOf[AbilityComponent])
  private val targetableComponent = ComponentMapper.getFor(classOf[TargetableComponent])
  private val factionComponent = ComponentMapper.getFor(classOf[FactionComponent])

  val selectedFamily: Family = Family.all(classOf[SelectedComponent]).get()
  val controllableFamily: Family = Family.all(classOf[SelectedComponent]).exclude(classOf[DeadComponent]).get()
  val selectableFamily: Family = Family.all(classOf[ActorComponent], classOf[SelectableComponent]).get()

  val characterSelectionChanged = new Event[Events.CharacterSelectionChanged]
  val useAbility = new Event[Events.UseAbility]

  val targetRequiredHandler = new EventHandler[TargetRequired] {
    override def handle(arg: TargetRequired): Unit = {
      usingAbility = Some(arg.abilityName)
    }
  }

  var usingAbility: Option[String] = None

  stage.addListener(new InputHandler())

  def clearSelection(hitEntity: Entity = null): Unit = {
    for (elem <- getEngine.getEntitiesFor(selectedFamily).asScala.toArray) {
      if (hitEntity == elem) return
      elem.remove(classOf[SelectedComponent])
    }
  }

  def select(entity: Entity): Unit = {
    if (!selectableFamily.matches(entity)) {
      characterSelectionChanged.fire(Events.CharacterSelectionChanged(None))
      return
    }

    clearSelection(entity)
    if (!selectedComponentMapper.has(entity)) entity.add(new SelectedComponent())

    val factionSystem = getEngine.getSystem(classOf[FactionSystem])
    val faction = factionSystem.getFaction(entity)

    if(faction.isDefined) {
      val fc = factionComponent.get(faction.get)
      println(s"faction: ${fc.faction}; gold: ${fc.gold}")
    }

    characterSelectionChanged.fire(Events.CharacterSelectionChanged(Some(entity)))
  }

  class InputHandler extends ActorGestureListener {
    override def pan(event: InputEvent, x: Float, y: Float, deltaX: Float, deltaY: Float): Unit = {
      val cameraSystem: CameraSystem = getEngine().getSystem(classOf[CameraSystem])
      cameraSystem.moveBy(deltaX, deltaY)
    }

    override def tap(event: InputEvent, x: Float, y: Float, count: Int, button: Int): scala.Unit = {
      if (Input.Buttons.LEFT == button) {
        println(s"${x}, ${y}, ${count}, ${button}")

        val actor = stage.hit(x, y, true)

        if (usingAbility.isEmpty) {
          if (actor == null) {
            clearSelection()
            characterSelectionChanged.fire(Events.CharacterSelectionChanged(None))
            return
          }

          if (!actor.isInstanceOf[GameActor]) return

          val entity = actor.asInstanceOf[GameActor].entity

          if (entity == null) return

          select(entity)
        }
        else if (usingAbility.isDefined) {
          println(s"using ${usingAbility.get}")

          var target: Option[Entity] = None
          var position: Option[Vector2] = None

          if(actor == null) {
            position = Some(new Vector2(x, y))
          }
          else if (actor.isInstanceOf[GameActor]) {
            target = Some(actor.asInstanceOf[GameActor].entity)
          }
          else {
            return
          }

          for (elem <- getEngine.getEntitiesFor(controllableFamily).asScala) {
            getEngine.getSystem(classOf[BehaviorSystem]).reset(elem)
            val orderComponent = new OrderComponent()
            orderComponent.target = target
            orderComponent.position = position
            orderComponent.ability = getEngine.getSystem(classOf[AbilitySystem]).getAbility(usingAbility.get, elem)
            elem.add(orderComponent)
            elem.remove(classOf[JobComponent])
          }

          usingAbility = None
        }
      }
      else if (Input.Buttons.RIGHT == button) {
        val actor = stage.hit(x, y, true)

        if (actor == null) {
          for (elem <- getEngine.getEntitiesFor(controllableFamily).asScala) {
            getEngine.getSystem(classOf[BehaviorSystem]).reset(elem)

            val orderComponent = new OrderComponent()
            orderComponent.position = Some(new Vector2(x, y))
            orderComponent.ability = getEngine.getSystem(classOf[AbilitySystem]).getAbility(AbilityComponent.ABILITY_MOVE, elem)
            elem.add(orderComponent)
            elem.remove(classOf[JobComponent])
          }
        }
        else {
          if (!actor.isInstanceOf[GameActor]) return
          val target = actor.asInstanceOf[GameActor].entity
          for (elem <- getEngine.getEntitiesFor(controllableFamily).asScala) {
            getEngine.getSystem(classOf[BehaviorSystem]).reset(elem)
            val orderComponent = new OrderComponent()
            orderComponent.target = Some(target)
            orderComponent.ability = getEngine.getSystem(classOf[AbilitySystem]).getAbility(AbilityComponent.ABILITY_MOVE, elem)
            elem.add(orderComponent)
            elem.remove(classOf[JobComponent])

          }
        }
      }
    }
  }

}
