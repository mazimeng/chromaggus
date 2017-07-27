package com.workasintended.chromaggus

import com.badlogic.ashley.core.{ComponentMapper, Engine, Entity}
import com.badlogic.ashley.signals.{Listener, Signal}
import com.badlogic.gdx.ai.btree.BehaviorTree
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.workasintended.chromaggus.component._
import com.workasintended.chromaggus.job.{JobListener, MoveTo}

/**
  * Created by mazimeng on 7/22/17.
  */
object Factory {
  var engine: Engine = _

  private val transformComponentMapper = ComponentMapper.getFor(classOf[TransformComponent])

  private lazy val char00 = new Texture("char00.png")
  private lazy val char01 = new Texture("char01.png")
  private lazy val char02 = new Texture("char02.png")

  private lazy val char01Frames = TextureRegion.split(char01, char01.getWidth / 3, char01.getHeight / 4)
  private lazy val char00Frames = TextureRegion.split(char00, char00.getWidth / 12, char00.getHeight / 8)
  private lazy val char02Frames = TextureRegion.split(char02, char00.getWidth / 12, char00.getHeight / 8)

  private lazy val icon = Service.assetManager.get("icon.png").asInstanceOf[Texture]
  private lazy val iconFrames = TextureRegion.split(icon, icon.getWidth / 16, icon.getHeight / 39)

//  def makeSelection(target: Entity): Entity = {
//    val transformComponentMapper = ComponentMapper.getFor(classOf[TransformComponent])
//
//    val itemTexture: Texture = Service.assetManager.get("spritesheet/selection.png")
//    val selectionTextureRegions = TextureRegion.split(itemTexture, itemTexture.getWidth() / 2, itemTexture.getHeight / 1)
//    val selectionAnimation = new Animation[TextureRegion](0.5f, selectionTextureRegions(0)(0), selectionTextureRegions(0)(1))
//
//    val targetTransformComponent = transformComponentMapper.get(target)
//
//    val actor = new GameActor(selectionAnimation)
//    actor.setSize(32, 32)
//    actor.setTouchable(Touchable.disabled)
//
//    val actorComponent = new ActorComponent(actor)
//    val transformComponent = new TransformComponent(targetTransformComponent.position)
//    val selectionComponent = new SelectionComponent(target)
//
//    val entity = new Entity()
//    entity.add(actorComponent)
//    entity.add(transformComponent)
//    entity.add(selectionComponent)
//
//    entity
//  }

  def makeSelectionActor(): GameActor = {
    val itemTexture: Texture = Service.assetManager.get("spritesheet/selection.png")
    val selectionTextureRegions = TextureRegion.split(itemTexture, itemTexture.getWidth() / 2, itemTexture.getHeight / 1)
    val selectionAnimation = new Animation[TextureRegion](0.5f, selectionTextureRegions(0)(0), selectionTextureRegions(0)(1))

    val actor = new GameActor(selectionAnimation)
    actor.setSize(32, 32)
    actor.setTouchable(Touchable.disabled)

    actor
  }

  def makeCharacter(pos: Vector2 = new Vector2()): Entity = {
    val animation = new Animation[TextureRegion](0.5f, char01Frames(0)(0), char01Frames(0)(2))

    val actor = new GameActor(animation)
    actor.setSize(32, 32)
    val actorComponent = new ActorComponent(actor)
    val transformComponent = new TransformComponent(pos)
    val movementComponent = new MovementComponent(pos)

    val entity = new Entity()
    entity.componentAdded.add(new Listener[Entity]() {
      override def receive(signal: Signal[Entity], t: Entity): scala.Unit = {
        println("component added")
      }
    })

    val behaviorComponent = new BehaviorComponent(makeBehavior("some"))
    behaviorComponent.behaviorTree.getObject.entity = entity

    entity.add(actorComponent)
    entity.add(transformComponent)
    entity.add(movementComponent)
    entity.add(new SelectableComponent())
    entity.add(behaviorComponent)

    actor.entity = entity

    entity
  }

  def makeDummy(position: Vector2 = new Vector2()): Entity = {
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
    val transformComponent = new TransformComponent(position)

    val entity = new Entity()
    entity.add(actorComponent)
    entity.add(transformComponent)

    actor.entity = entity

    entity
  }

  def makeMoveTo(target: Entity, position: Vector2): Entity = {
    val entity = new Entity()

    val moveTo = new MoveTo(target, position)
    val jobComponent = new JobComponent(moveTo)

    entity.add(jobComponent)
    entity
  }

  def makeFireball(caster: Entity, dest: Vector2): Entity = {
    val entity = new Entity()
    val moveTo = new MoveTo(entity, dest)
    moveTo.speed = 128f
    moveTo.listener = new JobListener {
      override def onDone(): Unit = {
        engine.removeEntity(entity)
      }
    }

    val animation = new Animation[TextureRegion](0.5f, iconFrames(6)(0))
    val actor = new GameActor(animation)
    actor.setSize(32f, 32f)

    val tc = transformComponentMapper.get(caster)

    val actorComponent = new ActorComponent(actor)
    val transformComponent = new TransformComponent(tc.position)
    val movementComponent = new MovementComponent(tc.position)
    val jobComponent = new JobComponent(moveTo)

    entity.add(actorComponent)
    entity.add(transformComponent)
    entity.add(movementComponent)
    entity.add(jobComponent)

    entity
  }

  def makeBehavior(name: String): BehaviorTree[Blackboard] = {
    val blackboard = new Blackboard()

    val library = BehaviorTreeLibraryManager.getInstance().getLibrary()

    val tree = library.createBehaviorTree(name, blackboard)

    tree
  }
}
