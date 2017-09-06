package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.{Component, Entity}
import com.badlogic.gdx.math.Circle

class OrderComponent extends Component {
  var target: Option[Entity] = None
  var location: Option[Circle] = None
  var order: String = _
}
