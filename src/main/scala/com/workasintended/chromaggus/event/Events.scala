package com.workasintended.chromaggus.event

import com.badlogic.ashley.core.Entity

object Events {
  case class FactionIncomeChanged(faction: Entity)
  case class AbilityUsed(ability: Entity)
  case class CharacterSelectionChanged(character: Option[Entity])
  case class TargetRequired()
  case class UseAbility(user: Entity, target: Entity, abilityName: String)
}


