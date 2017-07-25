package com.workasintended.chromaggus

import com.badlogic.ashley.core.{Engine, Entity}
import com.badlogic.ashley.signals.{Listener, Signal}
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui._
import com.workasintended.chromaggus.component.{ActorComponent, TransformComponent}
import com.workasintended.chromaggus.system.{ControlSystem, RenderSystem, SelectionSystem}


class GameScreen extends ScreenAdapter {
  val engine = new Engine()
  val stage = new Stage()

  init()

  override def render(delta: Float): scala.Unit = {
    engine.update(delta)
  }

  def init(): scala.Unit = {
    initAssets()
    val renderSystem = new RenderSystem(stage)
    val inputSystem = new ControlSystem(stage)
    val selectionHighlightSystem = new SelectionSystem()

    engine.addSystem(renderSystem)
    engine.addSystem(inputSystem)
    engine.addSystem(selectionHighlightSystem)

    val entity = makeCharacter()
    engine.addEntity(entity)
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

  def makeCharacter(): Entity = {
    val char00 = new Texture("char00.png")
    val char01 = new Texture("char01.png")
    val char02 = new Texture("char02.png")

    val char01Frames = TextureRegion.split(char01, char01.getWidth / 3, char01.getHeight / 4)
    val char00Frames = TextureRegion.split(char00, char00.getWidth / 12, char00.getHeight / 8)
    val char02Frames = TextureRegion.split(char02, char00.getWidth / 12, char00.getHeight / 8)

    val frames = new Array[TextureRegion](2)
    val animation = new Animation[TextureRegion](0.5f, char01Frames(0)(0), char01Frames(0)(2))

    val actor = new GameActor(animation)
    actor.setSize(32, 32)
    val actorComponent = new ActorComponent(actor)
    val transformComponent = new TransformComponent(new Vector2(32, 32))

    val entity = new Entity()
    entity.add(actorComponent)
    entity.add(transformComponent)

    actor.entity = entity

    entity
  }

  def makeSelection(): scala.Unit = {
    val itemTexture: Texture = Service.assetManager.get("spritesheet/selection.png")
    val selectionTextureRegions = TextureRegion.split(itemTexture, itemTexture.getWidth() / 2, itemTexture.getHeight / 1)
    val selectionAnimation = new Animation[TextureRegion](0.5f, selectionTextureRegions(0)(0), selectionTextureRegions(0)(1))
  }

//  def initStage(): Unit = {
//    val viewport = gameConfiguration.makeWorldViewport
//    stage = new WorldStage
//    stage.setGridMap(new GridMap(100))
//    stage.setShapeRenderer(new ShapeRenderer)
//    stage.setViewport(viewport)
//    this.inputHandler = this.gameConfiguration.makeInputListener(stage, player)
//    stage.addListener(this.inputHandler)
//    //stage.addAction(sequenceAction);
//  }

  override def resize(width: Int, height: Int): Unit = {
//    stage.getViewport.setWorldSize(width, height)
    stage.getViewport.update(width, height)
  }
}
