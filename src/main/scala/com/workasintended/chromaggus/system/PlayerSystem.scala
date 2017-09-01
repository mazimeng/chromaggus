package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.workasintended.chromaggus.component.FactionComponent


class PlayerSystem(val faction: Entity) extends EntitySystem {
  val factionFamily: Family = Family.all(classOf[FactionComponent]).get()
}
