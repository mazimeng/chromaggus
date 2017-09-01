package com.workasintended.chromaggus.event

import com.badlogic.ashley.core.Entity

object Events {
  case class FactionIncomeChanged(faction: Entity)
  case class AbilityUsed(ability: Entity)
}


