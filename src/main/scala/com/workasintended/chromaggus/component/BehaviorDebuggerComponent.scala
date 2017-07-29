package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.Component
import com.workasintended.chromaggus.BehaviorTreeViewer

/**
  * Created by mazimeng on 7/29/17.
  */
class BehaviorDebuggerComponent[E](val treeViewer: BehaviorTreeViewer[E]) extends Component {
}
