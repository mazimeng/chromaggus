package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.workasintended.chromaggus.component.FactionComponent

import scala.collection.mutable

class PlayerSystem() extends EntitySystem {
  val factionFamily: Family = Family.all(classOf[FactionComponent]).get()
  val characters: mutable.Set[Entity] = mutable.Set[Entity]()
  val factionComponent: ComponentMapper[FactionComponent] = ComponentMapper.getFor(classOf[FactionComponent])
  var factionName: String = _

  override def addedToEngine(engine: Engine) {
    super.addedToEngine(engine)

    engine.addEntityListener(factionFamily, new EntityListener() {
      override def entityAdded(entity: Entity): scala.Unit = {
        if(factionComponent.get(entity).faction == factionName) {
          characters.add(entity)
        }
      }

      override def entityRemoved(entity: Entity): scala.Unit = {
        if(factionComponent.get(entity).faction == factionName) {
          characters.remove(entity)
        }
      }
    })
  }

}
