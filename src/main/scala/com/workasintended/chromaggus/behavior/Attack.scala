package com.workasintended.chromaggus.behavior

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.workasintended.chromaggus.{Blackboard, Factory}
import com.workasintended.chromaggus.component.{CharacterComponent, JobComponent}
import com.workasintended.chromaggus.job.Use
import com.workasintended.chromaggus.system.AbilitySystem

/**
  * Created by mazimeng on 7/27/17.
  */
class Attack extends LeafTask[Blackboard]{
  val characterComponent = ComponentMapper.getFor(classOf[CharacterComponent])
  override def execute(): Status = {
    if(getObject.enemies.nonEmpty) {
      if(getStatus != Status.RUNNING) {
        val cc = characterComponent.get(getObject.entity)
        val as = Factory.engine.getSystem(classOf[AbilitySystem])
        val attack = new Use(getObject.entity, getObject.enemies.head, cc.equippedAbility, as)
        attack.onDone = () => reset()

        val jobComponent = new JobComponent(attack)

        getObject.entity.add(jobComponent)
      }

      Status.RUNNING
    }
    else Status.SUCCEEDED
  }

  override def copyTo(task: Task[Blackboard]): Task[Blackboard] = task
}