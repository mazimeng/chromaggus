package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.{Component, Entity}

import scala.collection.mutable

/**
  * Created by mazimeng on 7/31/17.
  */
class AbilityCollectionComponent extends Component{
  val abilities: mutable.Set[Entity] = mutable.Set()
}
