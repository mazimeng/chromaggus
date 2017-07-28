package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.ai.btree.BehaviorTree
import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.branch.{Selector, Sequence}
import com.badlogic.gdx.ai.btree.utils.{BehaviorTreeLibrary, BehaviorTreeLibraryManager, BehaviorTreeParser}
import com.workasintended.chromaggus.Blackboard
import com.workasintended.chromaggus.behavior._
import com.workasintended.chromaggus.component.BehaviorComponent

/**
  * Created by mazimeng on 7/27/17.
  */
class BehaviorSystem(family: Family = Family.all(classOf[BehaviorComponent]).get()) extends IteratingSystem(family) {
  private val behaviorComponent = ComponentMapper.getFor(classOf[BehaviorComponent])

  override def addedToEngine(engine: Engine): scala.Unit = {
    super.addedToEngine(engine)
    val libraryManager = BehaviorTreeLibraryManager.getInstance()
    val library: BehaviorTreeLibrary = new BehaviorTreeLibrary(BehaviorTreeParser.DEBUG_HIGH)
    libraryManager.setLibrary(library)

    val tree = makeMoveToTree()
    library.registerArchetypeTree("some", tree)
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

  private def makeMoveToTree(): BehaviorTree[Blackboard] = {
    val tree = new BehaviorTree[Blackboard]()

    val returnToStation = new Selector[Blackboard]()
    returnToStation.addChild(new InSafeZone())
    returnToStation.addChild(new AtStation())
    returnToStation.addChild(new ReturnToStation())

    val attack = new Sequence[Blackboard]()
    attack.addChild(new FindThreat())
    attack.addChild(new Attack())

    attack.setGuard(new Sequence[Blackboard](new Dummy("attack guard evaluated"), new InSafeZone()))

    tree.addChild(new Sequence(returnToStation, attack))

    tree
  }
}
