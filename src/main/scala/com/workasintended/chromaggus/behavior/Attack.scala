package com.workasintended.chromaggus.behavior

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.workasintended.chromaggus.Blackboard
import com.workasintended.chromaggus.component.{AbilityCollectionComponent, AbilityComponent}
import com.workasintended.chromaggus.system.AbilitySystem

/**
  * Created by mazimeng on 7/27/17.
  */
class Attack extends LeafTask[Blackboard]{
  val accm: ComponentMapper[AbilityCollectionComponent] = ComponentMapper.getFor(classOf[AbilityCollectionComponent])
  val acm: ComponentMapper[AbilityComponent] = ComponentMapper.getFor(classOf[AbilityComponent])
  override def execute(): Status = {
    if(getObject.enemies.nonEmpty) {
      if(getStatus != Status.RUNNING) {
        val abilitySystem = getObject.engine.getSystem(classOf[AbilitySystem])

        val abilityOption = abilitySystem.getFirstOffensive(getObject.entity)
        if(abilityOption.isEmpty) {
          Status.FAILED
        }
        else {
          abilitySystem.prepareAbility(abilityOption.get, getObject.entity, getObject.enemies.head)
        }
//        useAbility.fire(UseAbility(elem, target, AbilityComponent.ABILITY_DEFAULT))
//
//        val as = Factory.engine.getSystem(classOf[AbilitySystem])
//        val equippedAbility = as.getEquippedAbilities(getObject.entity).head
//        val attack = new Use(getObject.entity, getObject.enemies.head, equippedAbility, as)
//        attack.onDone = () => {
//          reset()
//          println("behavior: attack one. resetting")
//        }
//
//        val jobComponent = new JobComponent(attack)
//
//        getObject.entity.add(jobComponent)
//        println(s"behavior: attack job added, enemies: ${getObject.enemies.size}")
      }

      Status.RUNNING
    }
    else Status.SUCCEEDED
  }

  override def copyTo(task: Task[Blackboard]): Task[Blackboard] = task
}