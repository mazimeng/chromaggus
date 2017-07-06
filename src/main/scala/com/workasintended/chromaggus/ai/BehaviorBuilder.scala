package com.workasintended.chromaggus.ai

import com.badlogic.gdx.ai.btree.branch.{DynamicGuardSelector, Selector, Sequence}
import com.badlogic.gdx.ai.btree.decorator.Invert
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.badlogic.gdx.math.Vector2
import com.workasintended.chromaggus.ai.behavior._

/**
  * Created by mazimeng on 7/5/17.
  */
object BehaviorBuilder {
  def makeGuard: Task[Blackboard] = {
    val markPosition = new MarkStation
    val findThreatSequence = new Sequence[Blackboard]
    findThreatSequence.addChild(new ScanThreat(64))
    findThreatSequence.addChild(new TargetEnemy)
    var withinRadius = new WithinRadius(new GetPosition() {
      override def get(blackboard: Blackboard): Vector2 = {
        return blackboard.getStationPosition
      }
    }, 64)
    findThreatSequence.addChild(withinRadius)

    val attackTarget: AttackTarget = new AttackTarget
    val attack: DynamicGuardSelector[Blackboard] = new DynamicGuardSelector[Blackboard]
    attackTarget.setGuard(findThreatSequence)
    val stopEverything: StopEverything = new StopEverything
    stopEverything.setGuard(new Invert[Blackboard](new ScanThreat(64)))
    attack.addChild(attackTarget)
    withinRadius = new WithinRadius(new GetPosition() {
      override def get(blackboard: Blackboard): Vector2 = {
        return blackboard.getStationPosition
      }
    }, 8)
    val moveToPosition: MoveToPosition = new MoveToPosition(new GetPosition() {
      override def get(blackboard: Blackboard): Vector2 = {
        return blackboard.getStationPosition
      }
    }, 8)
    moveToPosition.setGuard(new Invert[Blackboard](withinRadius))
    val returnToStation: DynamicGuardSelector[Blackboard] = new DynamicGuardSelector[Blackboard]
    returnToStation.addChild(moveToPosition)
    return new Sequence[Blackboard](markPosition, new Selector[Blackboard](attack, returnToStation))
  }

}
