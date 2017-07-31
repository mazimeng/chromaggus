package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.ai.btree.BehaviorTree
import com.badlogic.gdx.ai.btree.branch.Parallel.Policy
import com.badlogic.gdx.ai.btree.branch.{Parallel, Selector, Sequence}
import com.badlogic.gdx.ai.btree.decorator.Invert
import com.badlogic.gdx.ai.btree.utils.{BehaviorTreeLibrary, BehaviorTreeLibraryManager, BehaviorTreeParser}
import com.workasintended.chromaggus.Blackboard
import com.workasintended.chromaggus.behavior._
import com.workasintended.chromaggus.component.{BehaviorComponent, DeadComponent, ManualComponent}

/**
  * Created by mazimeng on 7/27/17.
  */
class BehaviorSystem(family: Family = Family.all(classOf[BehaviorComponent]).exclude(classOf[ManualComponent], classOf[DeadComponent]).get()) extends IteratingSystem(family) {
  private val behaviorComponent = ComponentMapper.getFor(classOf[BehaviorComponent])

  override def addedToEngine(engine: Engine): scala.Unit = {
    super.addedToEngine(engine)
    val libraryManager = BehaviorTreeLibraryManager.getInstance()
    val library: BehaviorTreeLibrary = new BehaviorTreeLibrary(BehaviorTreeParser.DEBUG_HIGH)
    libraryManager.setLibrary(library)

    val tree = makeGuard()
    library.registerArchetypeTree("some", tree)

    engine.addEntityListener(family, new EntityListener() {
      override def entityAdded(entity: Entity): scala.Unit = {
        val component = behaviorComponent.get(entity)
        component.behaviorTree.reset()
      }

      override def entityRemoved(entity: Entity): scala.Unit = {
      }
    })
  }

  override def processEntity(entity: Entity, v: Float): scala.Unit = {
    val bc = behaviorComponent.get(entity)
    if(bc.elapsedSinceLastStep >= bc.interval) {
      bc.elapsedSinceLastStep = 0
      bc.behaviorTree.step()
    }
    else {
      bc.elapsedSinceLastStep += v
    }
  }

  private def makeGuard(): BehaviorTree[Blackboard] = {
    val tree = new BehaviorTree[Blackboard]()

    val returnToStation = new ReturnToStation()
    val returnToStationGuard = new Sequence(new Invert(new InSafeZone()), new Invert(new AtStation()))
    returnToStation.setGuard(returnToStationGuard)

    val attack = new Parallel[Blackboard](Policy.Sequence, new InSafeZone(), new FindThreat(), new Attack())

    tree.addChild(new Selector(returnToStation, attack))

    tree
  }
}
