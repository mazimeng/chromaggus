package com.workasintended.chromaggus

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.{Actor, Stage}
import com.badlogic.gdx.scenes.scene2d.ui._
import com.workasintended.chromaggus.system._

class GameScreen extends ScreenAdapter {
  val engine = new Engine()
  val stage = new Stage()
  val ui = new Stage()

  var frameCount = 0
  var timeElapsed = 0f

  Factory.engine = engine
  init()

  val fps: Container[Label] = makeFps()
  ui.addActor(fps)

  override def render(delta: Float): scala.Unit = {
    engine.update(delta)
    timeElapsed += delta
    frameCount += 1

    if(timeElapsed >= 0.5f) {
      fps.getActor.setText(s"fps: ${frameCount / timeElapsed}")
      timeElapsed = 0
      frameCount = 0
    }

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

  def makeFps(): Container[Label] = {
    val fps = new Container[Label]()
    fps.setFillParent(true)
    fps.setDebug(true, true)
    fps.top().left()

    val skin: Skin = Service.assetManager.get("uiskin.json")
    val label = new Label("fpx: 0", skin)
    fps.setActor(label)
    fps
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
