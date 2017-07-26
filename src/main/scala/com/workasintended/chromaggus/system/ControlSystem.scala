package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.{Gdx, Input, InputMultiplexer}
import com.workasintended.chromaggus.component.{JobComponent, SelectedComponent, SelectionComponent}
import com.workasintended.chromaggus.job.MoveTo
import com.workasintended.chromaggus.{Factory, GameActor}

import scala.collection.JavaConverters._

/**
  * Created by mazimeng on 7/21/17.
  */
class ControlSystem(val stage: Stage) extends EntitySystem {
  private val selectedComponentMapper = ComponentMapper.getFor(classOf[SelectedComponent])
  private val selectionComponentMapper = ComponentMapper.getFor(classOf[SelectionComponent])

  val selectedFamily: Family = Family.all(classOf[SelectedComponent]).get()
  val selectionFamily: Family = Family.all(classOf[SelectionComponent]).get()

  val multiplexer = new InputMultiplexer()
  multiplexer.addProcessor(stage)
  Gdx.input.setInputProcessor(multiplexer)

  stage.addListener(new InputHandler())

  class InputHandler extends ActorGestureListener{
    override def tap(event: InputEvent, x: Float, y: Float, count: Int, button: Int): scala.Unit = {
      println(s"tapped: ${event}, ${x}, ${y}, ${count}, ${button}")

      if (Input.Buttons.LEFT == button) {
        val actor = stage.hit(x, y, true)

        if (actor == null) {
          println("clicked on empty field")

          val selection = getEngine.getEntitiesFor(selectionFamily).asScala.toArray // instantiate a new collection to loop through
          for (x <- selection) getEngine.removeEntity(x)
          return
        }

        if (!actor.isInstanceOf[GameActor]) return

        val entity = actor.asInstanceOf[GameActor].entity

        val selection = Factory.makeSelection(entity)
        getEngine.addEntity(selection)
      }
      else if (Input.Buttons.RIGHT == button) {
        println(s"entities: ${getEngine.getEntities.size()}")
        for (elem <- getEngine.getEntitiesFor(selectedFamily).asScala) {
          val moveTo = new MoveTo(elem, new Vector2(x, y))
          val jobComponent = new JobComponent(moveTo)

          elem.remove(classOf[JobComponent])
          elem.add(jobComponent)
        }
      }
    }
  }
}
