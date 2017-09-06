package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.{Component, Entity}

class UseComponent(val user: Entity, val target: Entity, val ability: Entity) extends Component {
  var useProgress = 0f
  var useState: Int = UseComponent.STATE_IDLE
}

object UseComponent {
  val STATE_IDLE: Int = 0
  val STATE_PREPARING: Int = 1
  val STATE_PREPARED: Int = 2
}
