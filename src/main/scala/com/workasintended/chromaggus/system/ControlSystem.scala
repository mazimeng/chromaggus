package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.{Gdx, Input, InputMultiplexer}
import com.workasintended.chromaggus.component.{SelectedComponent, SelectionComponent, TransformComponent}
import com.workasintended.chromaggus.{Factory, GameActor}

import scala.collection.JavaConverters._

/**
  * Created by mazimeng on 7/21/17.
  */
class ControlSystem(val stage: Stage) extends EntitySystem {
  private val transformComponentMapper = ComponentMapper.getFor(classOf[TransformComponent])
  private val selectedComponentMapper = ComponentMapper.getFor(classOf[SelectedComponent])

  val selectedFamily: Family = Family.all(classOf[SelectedComponent]).get()
  val selectionFamily: Family = Family.all(classOf[SelectionComponent]).get()

  val multiplexer = new InputMultiplexer()
  multiplexer.addProcessor(stage)
  Gdx.input.setInputProcessor(multiplexer)

  stage.addListener(new ActorGestureListener() {
    override def tap(event: InputEvent, x: Float, y: Float, count: Int, button: Int): scala.Unit = {
      println(s"tapped: ${event}, ${x}, ${y}, ${count}, ${button}")

      if(Input.Buttons.LEFT == button) {
        val actor = stage.hit(x, y, true)

        if(actor == null) {
          println("clicked on empty field")

          val selection = getEngine.getEntitiesFor(selectionFamily)
          for(x <- selection.asScala) {
            getEngine.removeEntity(x)
          }

          return
        }

        if(!actor.isInstanceOf[GameActor]) return

        val entity = actor.asInstanceOf[GameActor].entity

        if(selectedComponentMapper.has(entity)) return

        val selection = Factory.makeSelection(entity)
        getEngine.addEntity(selection)
      }
      else if(Input.Buttons.RIGHT == button) {
        for (elem <- getEngine.getEntitiesFor(selectedFamily).asScala) {
          val tc = transformComponentMapper.get(elem)
          tc.position.set(x, y)
        }
      }
    }
  })
}
