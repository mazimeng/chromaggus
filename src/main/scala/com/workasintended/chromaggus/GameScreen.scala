package com.workasintended.chromaggus

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui._
import com.workasintended.chromaggus.system._

class GameScreen extends ScreenAdapter {
  val engine = new Engine()
  val stage = new Stage()
  val ui = new Stage()
  Factory.engine = engine

  init()

  override def render(delta: Float): scala.Unit = {
    engine.update(delta)
  }

  def init(): scala.Unit = {
    initAssets()
    val renderSystem = new RenderSystem(stage, ui)
    val inputSystem = new ControlSystem(stage)
    val selectionHighlightSystem = new SelectionSystem()
    val jobSystem = new JobSystem()
    val behaviorSystem = new BehaviorSystem()
    val behaviorDebugginSystem = new BehaviorDebuggingSystem(ui)

    engine.addSystem(renderSystem)
    engine.addSystem(inputSystem)
    engine.addSystem(selectionHighlightSystem)
    engine.addSystem(jobSystem)
    engine.addSystem(behaviorSystem)
    engine.addSystem(behaviorDebugginSystem)

    engine.addEntity(Factory.makeCharacter(new Vector2(0, 0)))
    engine.addEntity(Factory.makeCharacter(new Vector2(256, 256)))
  }

  def initAssets(): scala.Unit = {
    Service.assetManager.load("icon.png", classOf[Texture])
    Service.assetManager.load("icon.06.png", classOf[Texture])
    Service.assetManager.load("spritesheet/selection.png", classOf[Texture])
    Service.assetManager.load("sound/level_up.wav", classOf[Sound])
    Service.assetManager.load("sound/fireball.wav", classOf[Sound])
    Service.assetManager.load("sound/melee.wav", classOf[Sound])
    Service.assetManager.load("uiskin.json", classOf[Skin])
    Service.assetManager.finishLoading()
  }

  override def resize(width: Int, height: Int): Unit = {
    stage.getViewport.update(width, height)
    ui.getViewport.update(width, height)
  }
}
