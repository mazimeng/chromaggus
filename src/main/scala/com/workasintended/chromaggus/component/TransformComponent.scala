package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

/**
  * Created by mazimeng on 7/25/17.
  */
class TransformComponent(pos: Vector2) extends Component {
  val position = new Vector2(pos)

  def this() {
    this(new Vector2())
  }
}
