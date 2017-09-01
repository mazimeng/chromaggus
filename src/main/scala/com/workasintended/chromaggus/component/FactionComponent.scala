package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.{Component, Entity}

import scala.collection.mutable

/**
  * Created by mazimeng on 7/22/17.
  */
class FactionComponent(val faction: String) extends Component {
  var gold: Long = 0L
  var characters: mutable.Set[Entity] = mutable.Set()
  var cities: mutable.Set[Entity] = mutable.Set()
}