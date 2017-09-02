package com.workasintended.chromaggus

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.gdx.math.{Circle, Vector2}
import com.workasintended.chromaggus.component.{AbilityCollectionComponent, DeadComponent, JobComponent, PositionComponent}

import scala.collection.JavaConverters._

/**
  * Created by mazimeng on 7/27/17.
  */
class Blackboard {
  val movementComponentMapper: ComponentMapper[PositionComponent] = ComponentMapper.getFor(classOf[PositionComponent])
  val enemyFamily: Family = Family.all(classOf[AbilityCollectionComponent]).exclude(classOf[DeadComponent]).get()

  var job: JobComponent = _
  var entity: Entity = _
  var threatRange2: Float = 128f*128f

  val safe: Circle = new Circle()
  val station: Circle = new Circle()

  var enemies: Seq[Entity] = _

  def entityPosition(): Vector2 = movementComponentMapper.get(entity).position

  def findEnemies(): Seq[Entity] = {
    val position = movementComponentMapper.get(entity).position

    val enemies: Seq[Entity] = Factory.engine.getEntitiesFor(enemyFamily).asScala.flatMap((x: Entity) => {
      val enemyPosition: Vector2 = movementComponentMapper.get(x).position
      val dst2 = enemyPosition.dst2(position)

      if (x == entity || dst2 > threatRange2) {
        None
      }
      else {
        Some(x)
      }
    }).toSeq

    enemies
  }

  def isStationed: Boolean = {
    val position = movementComponentMapper.get(entity).position
    station.contains(position)
  }

  def isSafe: Boolean = {
    val position = movementComponentMapper.get(entity).position
    safe.contains(position)
  }
}
