package com.workasintended.chromaggus

import com.badlogic.ashley.core.{ComponentMapper, Engine, Entity, EntityListener}
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
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
}
