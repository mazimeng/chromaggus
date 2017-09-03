package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.ai.btree.BehaviorTree
import com.workasintended.chromaggus.Blackboard

/**
  * Created by mazimeng on 7/27/17.
  */
class BehaviorComponent(val behaviorTree: BehaviorTree[Blackboard]) extends Component {
  var interval = 0.2f
  var elapsedSinceLastStep: Float = interval
  var isEnabled = false
}
