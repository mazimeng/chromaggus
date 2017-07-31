package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.{Gdx, Input, InputMultiplexer}
import com.workasintended.chromaggus.component._
import com.workasintended.chromaggus.job.{MoveTo, Use}
import com.workasintended.chromaggus.{Factory, GameActor}

import scala.collection.JavaConverters._

/**
  * Created by mazimeng on 7/21/17.
  */
class ControlSystem(val stage: Stage) extends EntitySystem {
  private val selectedComponentMapper = ComponentMapper.getFor(classOf[SelectedComponent])
  private val characterComponent = ComponentMapper.getFor(classOf[CharacterComponent])

  val selectedFamily: Family = Family.all(classOf[SelectedComponent]).get()
  val controllableFamily: Family = Family.all(classOf[SelectedComponent]).exclude(classOf[DeadComponent]).get()
  val selectableFamily: Family = Family.all(classOf[ActorComponent], classOf[SelectableComponent]).get()

  val multiplexer = new InputMultiplexer()
  multiplexer.addProcessor(stage)
  Gdx.input.setInputProcessor(multiplexer)

  stage.addListener(new InputHandler())

  class InputHandler extends ActorGestureListener {
    private def clearSelection(hitEntity: Entity = null): scala.Unit = {
      for (elem <- getEngine.getEntitiesFor(selectedFamily).asScala.toArray) {
        if(hitEntity == elem) return
        elem.remove(classOf[SelectedComponent])
      }
    }
    override def tap(event: InputEvent, x: Float, y: Float, count: Int, button: Int): scala.Unit = {
      println(s"tapped: ${event}, ${x}, ${y}, ${count}, ${button}")

      if (Input.Buttons.LEFT == button) {
        val actor = stage.hit(x, y, true)

        if (actor == null) {
          clearSelection()
          return
        }

        if (!actor.isInstanceOf[GameActor]) return

        val entity = actor.asInstanceOf[GameActor].entity

        if(entity == null) return

        if(!selectableFamily.matches(entity)) return

        clearSelection(entity)
        if(!selectedComponentMapper.has(entity)) entity.add(new SelectedComponent())
      }
      else if (Input.Buttons.RIGHT == button) {
        println(s"entities: ${getEngine.getEntities.size()}")

        val actor = stage.hit(x, y, true)

        if(actor == null) {
          for (elem <- getEngine.getEntitiesFor(controllableFamily).asScala) {
            val moveTo = new MoveTo(elem, new Vector2(x, y))
            moveTo.onDone = () => elem.remove(classOf[ManualComponent])
            val jobComponent = new JobComponent(moveTo)

            elem.add(jobComponent)
            elem.add(new ManualComponent())
          }
        }
        else {
          if (!actor.isInstanceOf[GameActor]) return
          val target = actor.asInstanceOf[GameActor].entity
          val abilitySystem = getEngine.getSystem(classOf[AbilitySystem])

          for (elem <- getEngine.getEntitiesFor(controllableFamily).asScala) {
            val ability = characterComponent.get(elem).equippedAbility
            val follow = new Use(elem, target, ability, abilitySystem)
            val jobComponent = new JobComponent(follow)

            elem.add(jobComponent)
          }
        }
      }
    }
  }
}
