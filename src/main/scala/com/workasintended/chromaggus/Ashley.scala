package com.workasintended.chromaggus

import com.badlogic.ashley.core._
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent, InputListener, Stage}

/**
  * Created by mazimeng on 7/19/17.
  */
object Ashley {
  def main (args: Array[String]): scala.Unit = {
    val engine = new Engine()
    val stage: Stage = null
    val actor: Actor = null

    val stageComponent = new StageComponent(stage)
    val characterHightlightGraphicalComponent = new CharacterHightlightGraphicalComponent(actor)
    val renderableComponent = new RenderableComponent(actor)

    val playerControlSystem = new PlayerControlSystem(stage)
    var character = new Entity()
    character.add(renderableComponent)
    character.add(characterHightlightGraphicalComponent)
    character.add(stageComponent)
    engine.addEntity(character)

    character = new Entity()
    character.add(characterHightlightGraphicalComponent)
    engine.addEntity(character)


    val family: Family = Family.one(classOf[CharacterHightlightGraphicalComponent], classOf[RenderableComponent]).get()
    val entities = engine.getEntitiesFor(family)
    println(entities.size())
  }

  class StageComponent(val stage: Stage) extends Component {}
  class RenderableComponent(private val actor: Actor) extends Component {}
  class CharacterHightlightGraphicalComponent(private val actor: Actor) extends RenderableComponent(actor) {}

  class CharacterComponent extends Component {}
  class PlayerControlSystem(private val stage: Stage) extends EntitySystem {}
  class RenderSystem(private val family: Family = Family.all(classOf[RenderableComponent]).get()) extends IteratingSystem(family) {
    override def processEntity(entity: Entity, delta: Float): scala.Unit = {

    }
  }
}
