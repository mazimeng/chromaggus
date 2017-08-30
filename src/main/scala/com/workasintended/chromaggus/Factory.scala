package com.workasintended.chromaggus

import com.badlogic.ashley.core.{ComponentMapper, Engine, Entity, Family}
import com.badlogic.gdx.ai.btree.BehaviorTree
import com.badlogic.gdx.ai.btree.branch.Sequence
import com.badlogic.gdx.ai.btree.decorator.Invert
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.{OrthographicCamera, Texture}
import com.badlogic.gdx.graphics.g2d.{Animation, BitmapFont, TextureRegion}
import com.badlogic.gdx.maps.tiled.{TiledMap, TmxMapLoader}
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.{Circle, Vector2}
import com.badlogic.gdx.scenes.scene2d.{Stage, Touchable}
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.workasintended.chromaggus.actor.HealthActor
import com.workasintended.chromaggus.behavior._
import com.workasintended.chromaggus.component._
import com.workasintended.chromaggus.job.MoveTo

import scala.collection.JavaConverters._

/**
  * Created by mazimeng on 7/22/17.
  */
object Factory {
  var engine: Engine = _
  lazy val bitmapFont = new BitmapFont()
  private val transformComponentMapper = ComponentMapper.getFor(classOf[TransformComponent])
  private val movementComponentMapper = ComponentMapper.getFor(classOf[MovementComponent])
  private val abilityComponent = ComponentMapper.getFor(classOf[AbilityComponent])

  private lazy val char00 = new Texture("char00.png")
  private lazy val char01 = new Texture("char01.png")
  private lazy val char02 = new Texture("char02.png")
  private lazy val cityTexture = new Texture("city.png")

  private lazy val cityFrames = TextureRegion.split(cityTexture, cityTexture.getWidth, cityTexture.getHeight)
  private lazy val char01Frames = TextureRegion.split(char01, char01.getWidth / 3, char01.getHeight / 4)
  private lazy val char00Frames = TextureRegion.split(char00, char00.getWidth / 12, char00.getHeight / 8)
  private lazy val char02Frames = TextureRegion.split(char02, char00.getWidth / 12, char00.getHeight / 8)

  private lazy val icon = Service.assetManager.get("icon.png").asInstanceOf[Texture]
  private lazy val iconFrames = TextureRegion.split(icon, icon.getWidth / 16, icon.getHeight / 39)

  import org.kohsuke.randname.RandomNameGenerator

  val randomNames = new RandomNameGenerator(0)

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
    val entity = new Entity()

    val animation = new Animation[TextureRegion](0.5f, char01Frames(0)(0), char01Frames(0)(2))

    val actor = new GameActor(animation)
    actor.setSize(32, 32)
    val actorComponent = new ActorComponent(actor)
    val transformComponent = new TransformComponent(pos)
    val movementComponent = new MovementComponent(pos)
    val attributeComponent = new AttributeComponent()
    attributeComponent.health = 100

    val blackboard = new Blackboard()
    blackboard.entity = entity
    blackboard.station.set(pos, 64f)
    blackboard.safe.set(pos, 128f)

    val behaviorComponent = new BehaviorComponent(makeBehavior("some", entity, blackboard))

    entity.add(actorComponent)
    entity.add(transformComponent)
    entity.add(movementComponent)
    entity.add(new SelectableComponent())
    //    entity.add(behaviorComponent)
    entity.add(attributeComponent)
    entity.add(new TargetableComponent())
    entity.add(new FactionComponent("horde"))
    entity.add(new NameComponent(randomNames.next()))

    actor.entity = entity

    val skin: Skin = Service.assetManager.get("uiskin.json")
    val treeViewer = new BehaviorTreeViewer[Blackboard](behaviorComponent.behaviorTree, skin)
    val behaviorDebuggerComponent: BehaviorDebuggerComponent[Blackboard] = new BehaviorDebuggerComponent(treeViewer)
    //    entity.add(behaviorDebuggerComponent)

    val healthActor = new HealthActor(attributeComponent)
    actor.addActor(healthActor)

    val abilityCollectionComponent = new AbilityCollectionComponent()
    val fireball = makeFireballAbility()
    abilityCollectionComponent.abilities(0) = fireball

    entity.add(abilityCollectionComponent)

    engine.addEntity(fireball)


    entity
  }

  def makeCity(pos: Vector2 = new Vector2()): Entity = {
    val entity = new Entity()

    val animation = new Animation[TextureRegion](0.0f, cityFrames(0)(0))

    val actor = new GameActor(animation)
    actor.setSize(32, 32)
    val actorComponent = new ActorComponent(actor)
    val transformComponent = new TransformComponent(pos)

    entity.add(actorComponent)
    entity.add(transformComponent)
    entity.add(new SelectableComponent())

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

  def makeFireballAbility(): Entity = {
    val entity = new Entity()
    val abilityComponent = new AbilityComponent()
    abilityComponent.abilityType = AbilityComponent.TYPE_MISSLE
    abilityComponent.preparation = 2f
    abilityComponent.cooldown = 1f
    abilityComponent.range = 128f
    abilityComponent.damage = 40
    abilityComponent.name = "fireball"

    val animation = new Animation[TextureRegion](0.5f, iconFrames(6)(0))
    val actor = new GameActor(animation)
    actor.setSize(32f, 32f)
    abilityComponent.actor = actor

    entity.add(abilityComponent)
    entity
  }

  def makeBehavior(name: String, entity: Entity, blackboard: Blackboard): BehaviorTree[Blackboard] = {
    val library = BehaviorTreeLibraryManager.getInstance().getLibrary()
    val tree = library.createBehaviorTree(name, blackboard)

    tree
  }

  def makeGuardBehavior(): BehaviorTree[Blackboard] = {
    val tree = new BehaviorTree[Blackboard]()

    val returnToStation = new ReturnToStation()
    val returnToStationGuard = new Sequence(new Invert(new InSafeZone()), new Invert(new AtStation()))
    returnToStation.setGuard(returnToStationGuard)

    val attack = new Sequence[Blackboard]()
    attack.addChild(new FindThreat())
    attack.addChild(new Attack())

    attack.setGuard(new Sequence[Blackboard](new Dummy("attack guard evaluated"), new InSafeZone()))

    tree.addChild(new Sequence(returnToStation, attack))

    tree
  }

  def makeWorldRenderer(stage: Stage): OrthogonalTiledMapRenderer = {
    val map: TiledMap = new TmxMapLoader(new InternalFileHandleResolver()).load("episode01.tmx")
    val unitScale: Float = 2f
    val renderer: OrthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(map, unitScale, stage.getBatch())
    val camera = stage.getCamera.asInstanceOf[OrthographicCamera]


    renderer
  }
}
