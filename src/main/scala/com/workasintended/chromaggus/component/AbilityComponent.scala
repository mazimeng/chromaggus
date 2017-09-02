package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.{Component, Entity}
import com.workasintended.chromaggus.GameActor

/**
  * Created by mazimeng on 7/30/17.
  */
class AbilityComponent extends Component {
  var name: String = _
  var preparation: Float = 0f
  var cooldown: Float = 0f
  var range: Float = 0f
  def range2: Float = range*range
  var state: Int = AbilityComponent.STATE_READY
  var progress: Float = 0f
  var abilityType: Int = AbilityComponent.TYPE_MISSLE
  var damage: Int = 0
  var actor: GameActor = _
  var effect: Entity = _
  var proficiency: Float = 0 // 0 - 100
  var proficiencyGrowth: Float = 1f //increment per use
  var isEquipped = false
}

object AbilityComponent {
  val STATE_READY: Int = 1
  val STATE_COOLINGDOWN: Int = 3
  var TYPE_MISSLE = 1

  val ABILITY_DEFAULT: String = "default"
  val ABILITY_SIEGE: String = "siege"
  val ABILITY_FIREBALL: String = "fireball"
}