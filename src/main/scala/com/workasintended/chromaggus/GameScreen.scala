package com.workasintended.chromaggus

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.utils.viewport._
import com.badlogic.gdx.{Gdx, InputMultiplexer, ScreenAdapter}
import com.kotcrab.vis.ui.VisUI
import com.workasintended.chromaggus.system._

class GameScreen extends ScreenAdapter {
  val viewport: FillViewport = new FillViewport(Gdx.graphics.getWidth()*0.6f, Gdx.graphics.getHeight()*0.6f)
  val uiViewport: StretchViewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())
  val engine = new Engine()
  val stage = new Stage(viewport)
  val ui = new Stage(uiViewport)

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

    if (timeElapsed >= 0.5f) {
      fps.getActor.setText(s"fps: ${frameCount / timeElapsed}")
      timeElapsed = 0
      frameCount = 0
    }
  }

  def init(): scala.Unit = {
    initAssets()

    val worldRenderer = Factory.makeWorldRenderer(stage)
    val renderSystem = new RenderSystem(stage, ui, worldRenderer)
    val controlSystem = new ControlSystem(stage)
    val selectionHighlightSystem = new SelectionSystem()
    val jobSystem = new JobSystem()
    val behaviorSystem = new BehaviorSystem()
    val behaviorDebugginSystem = new BehaviorDebuggingSystem(ui)
    val abilitySystem = new AbilitySystem()
    val cameraSystem = new CameraSystem(stage)
    val playerSystem = new PlayerSystem()
    val abilityUiSystem = new AbilityUiSystem(ui)

    playerSystem.factionName = "horde"

    engine.addSystem(playerSystem)
    engine.addSystem(controlSystem)
    engine.addSystem(selectionHighlightSystem)
    engine.addSystem(jobSystem)
    engine.addSystem(behaviorSystem)
    engine.addSystem(new DeathSystem())
    engine.addSystem(behaviorDebugginSystem)
    engine.addSystem(abilitySystem)
    engine.addSystem(cameraSystem)
    engine.addSystem(renderSystem)
    engine.addSystem(abilityUiSystem)

    engine.addEntity(Factory.makeCity(new Vector2(13 * 32, 5 * 32)))
    engine.addEntity(Factory.makeCharacter(new Vector2(0, 0)))
    engine.addEntity(Factory.makeCharacter(new Vector2(128, 128)))
    engine.addEntity(Factory.makeCharacter(new Vector2(256, 256)))


    val multiplexer = new InputMultiplexer()
    multiplexer.addProcessor(ui)
    multiplexer.addProcessor(stage)
    Gdx.input.setInputProcessor(multiplexer)
  }

  def makeFps(): Container[Label] = {
    val fps = new Container[Label]()
    fps.setFillParent(true)
    fps.setDebug(true, true)
    fps.bottom().left()

    val skin: Skin = Service.assetManager.get("uiskin.json")
    val label = new Label("fpx: 0", skin)
    fps.setActor(label)
    fps
  }

  def initAssets(): scala.Unit = {
    VisUI.load()

    Service.assetManager.load("city.png", classOf[Texture])
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
    val w: Int = width
    val h: Int = height
    stage.getViewport.update(w, h)
    ui.getViewport.update(w, h)
  }
}
