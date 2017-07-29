package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.Component

/**
  * Created by mazimeng on 7/29/17.
  */
class AttributeComponent extends Component {
  private var _health: Int = 0
  var strength: Int = 0
  var intelligence: Int = 0

  def health: Int = _health
  def health_=(h: Int): scala.Unit = { _health = if(h > 0) h else 0 }
}
