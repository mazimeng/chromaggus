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
    val family: Family = null

    val stage: Stage = null
    val actor: Actor = null

    val stageComponent = new StageComponent(stage)
    val playerControlSystem = new PlayerControlSystem(stage)
    val player = new Entity()
    player.add(stageComponent)

    engine.addEntity(player)

    engine.addSystem(playerControlSystem)
  }

  class StageComponent(val stage: Stage) extends Component {}

  class PlayerControlSystem(private val stage: Stage) extends EntitySystem {
    stage.addListener(new InputHandler())

    class InputHandler extends ActorGestureListener {
      override def tap(event: InputEvent, x: Float, y: Float, count: Int, button: Int): scala.Unit = {
        val actor = stage.hit(x, y, true)
      }
    }
  }
}
