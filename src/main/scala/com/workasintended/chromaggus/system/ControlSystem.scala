package com.workasintended.chromaggus.system

import com.badlogic.ashley.core.{ComponentMapper, EntitySystem, Family}
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.{Gdx, InputMultiplexer}
import com.workasintended.chromaggus.component.{ActorComponent, SelectionComponent}

/**
  * Created by mazimeng on 7/21/17.
  */
class ControlSystem(val stage: Stage) extends EntitySystem {
//  private val characterComponentMapper = ComponentMapper.getFor(classOf[CharacterComponent])
  private val selectionComponentMapper = ComponentMapper.getFor(classOf[SelectionComponent])

//  val family: Family = Family.one(classOf[CharacterComponent]).get()
  val multiplexer = new InputMultiplexer()
  multiplexer.addProcessor(stage)
  Gdx.input.setInputProcessor(multiplexer)

  stage.addListener(new ActorGestureListener() {
    override def tap(event: InputEvent, x: Float, y: Float, count: Int, button: Int): scala.Unit = {
      val actor = stage.hit(x, y, true)
      if(!actor.isInstanceOf[ActorComponent]) return

      val characterActor = actor.asInstanceOf[ActorComponent]
      val entity = characterActor.entity

      println(s"tapped: ${event}, ${x}, ${y}, ${count}, ${button}, ${actor}")

      if(!selectionComponentMapper.has(entity)) return

      entity.add(new SelectionComponent())
//      val component = characterComponentMapper.get(entity)
//      component.selected = !component.selected
    }
  })
}
