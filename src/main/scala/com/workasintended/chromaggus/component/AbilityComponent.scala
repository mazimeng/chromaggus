package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.{Component, Entity}

/**
  * Created by mazimeng on 7/30/17.
  */
class AbilityComponent extends Component {
  var preparation: Float = 0f
  var cooldown: Float = 0f
  var range: Float = 0f
  def range2: Float = range*range
  var state: Int = AbilityComponent.STATE_READY
  var progress: Float = 0f

  var effect: Entity = _
}

object AbilityComponent {
  val STATE_READY: Int = 1
  val STATE_PREPARING: Int = 2
  val STATE_COOLINGDOWN: Int = 3
}