package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.{Component, Entity}

/**
  * Created by mazimeng on 7/22/17.
  */
class FactionComponent(val faction: String) extends Component {
  var gold: Long = 0L
  var characters: Set[Entity] = Set()
}