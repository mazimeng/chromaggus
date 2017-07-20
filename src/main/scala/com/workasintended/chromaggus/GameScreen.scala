package com.workasintended.chromaggus

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.{Gdx, InputMultiplexer, ScreenAdapter}
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Animation, Batch, TextureRegion}
import com.badlogic.gdx.scenes.scene2d.{Actor, Stage}
import com.badlogic.gdx.scenes.scene2d.ui._


class GameScreen extends ScreenAdapter {
  val engine = new Engine()
  init()

  override def render(delta: Float): scala.Unit = {
    engine.update(delta)
  }

  def init(): scala.Unit = {
    initAssets()

    val stage = new Stage()

    val renderSystem = new RenderSystem(stage)

    engine.addSystem(renderSystem)

    val actor = makeCharacter()
    stage.addActor(actor)
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

  def makeCharacter(): Actor = {
    val char00 = new Texture("char00.png")
    val char01 = new Texture("char01.png")
    val char02 = new Texture("char02.png")

    val char01Frames = TextureRegion.split(char01, char01.getWidth / 3, char01.getHeight / 4)
    val char00Frames = TextureRegion.split(char00, char00.getWidth / 12, char00.getHeight / 8)
    val char02Frames = TextureRegion.split(char02, char00.getWidth / 12, char00.getHeight / 8)

    val frames = new Array[TextureRegion](2)
    val animation = new Animation[TextureRegion](0.5f, char01Frames(0)(0), char01Frames(0)(2))

    val itemTexture: Texture = Service.assetManager.get("spritesheet/selection.png")
    val selectionTextureRegions = TextureRegion.split(itemTexture, itemTexture.getWidth() / 2, itemTexture.getHeight / 1)
    val selectionAnimation = new Animation[TextureRegion](0.5f, selectionTextureRegions(0)(0), selectionTextureRegions(0)(1))
    val actor = new CharacterActor(animation, selectionAnimation)

    actor.setSize(32, 32)

    actor
  }

  class CharacterActor(val animation: Animation[TextureRegion], val selection: Animation[TextureRegion], var selected: Boolean = false) extends Actor {
    var stateTime = 0f

    override def draw(batch: Batch, parentAlpha: Float): scala.Unit = {
      stateTime += Gdx.graphics.getDeltaTime

      renderCharacter(batch, parentAlpha)
      renderSelection(batch, parentAlpha)
    }

    private def renderCharacter(batch: Batch, parentAlpha: Float): scala.Unit = {
      val frame = animation.getKeyFrame(stateTime, true)
      batch.draw(frame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation())
    }

    private def renderSelection(batch: Batch, parentAlpha: Float): scala.Unit = {
      if(!selected) return

      val frame = selection.getKeyFrame(stateTime, true)
      batch.draw(frame, getX, getY, getOriginX, getOriginY, getWidth, getHeight, getScaleX, getScaleY, getRotation)
    }
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

  def initInputs(stage: Stage): scala.Unit = {
    val multiplexer = new InputMultiplexer
    multiplexer.addProcessor(stage)
    Gdx.input.setInputProcessor(multiplexer)
  }

//  override def resize(width: Int, height: Int): Unit = {
//    stage.getViewport.setWorldSize(width, height)
//    stage.getViewport.update(width, height)
//    val zoom = 1280 * 0.4f / width
//    gui.getViewport.setWorldSize(width * zoom, height * zoom)
//    gui.getViewport.update(width, height, true)
//  }
}
