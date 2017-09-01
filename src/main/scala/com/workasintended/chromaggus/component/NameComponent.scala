package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.Component

/**
  * Created by mazimeng on 7/22/17.
  */
class NameComponent(val name: String) extends Component {
  override def toString: String = name
}