package com.workasintended.chromaggus

import com.badlogic.ashley.core.{ComponentMapper, Engine, Entity, EntityListener}
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.workasintended.chromaggus.component.{ActorComponent, SelectionComponent, TransformComponent}

/**
  * Created by mazimeng on 7/22/17.
  */
object Factory {
  def makeSelection(target: Entity): Entity = {
    val transformComponentMapper = ComponentMapper.getFor(classOf[TransformComponent])

    val itemTexture: Texture = Service.assetManager.get("spritesheet/selection.png")
    val selectionTextureRegions = TextureRegion.split(itemTexture, itemTexture.getWidth() / 2, itemTexture.getHeight / 1)
    val selectionAnimation = new Animation[TextureRegion](0.5f, selectionTextureRegions(0)(0), selectionTextureRegions(0)(1))

    val targetTransformComponent = transformComponentMapper.get(target)

    val actor = new GameActor(selectionAnimation)
    actor.setSize(32, 32)
    actor.setTouchable(Touchable.disabled)

    val actorComponent = new ActorComponent(actor)
    val transformComponent = new TransformComponent(targetTransformComponent.position)
    val selectionComponent = new SelectionComponent(target)

    val entity = new Entity()
    entity.add(actorComponent)
    entity.add(transformComponent)
    entity.add(selectionComponent)

    entity
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
}
