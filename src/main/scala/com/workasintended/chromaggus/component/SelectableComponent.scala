package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.Component
import com.workasintended.chromaggus.{Factory, GameActor}

/**
  * Created by mazimeng on 7/22/17.
  */
class SelectableComponent extends Component {
  lazy val selectionActor: GameActor = Factory.makeSelectionActor()
}
