package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.{Component, Entity}
import com.badlogic.gdx.math.Vector2

class OrderComponent extends Component {
  var target: Option[Entity] = None
  var position: Option[Vector2] = None
  var order: String = _
  var ability: Option[Entity] = None
}
