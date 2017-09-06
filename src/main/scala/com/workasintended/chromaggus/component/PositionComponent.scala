package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

/**
  * Created by mazimeng on 7/26/17.
  */
class PositionComponent(pos: Vector2 = new Vector2()) extends Component {
  val position = new Vector2(pos)
}
