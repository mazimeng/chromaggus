package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.{Component, Entity}

/**
  * Created by mazimeng on 7/31/17.
  */
class AbilityCollectionComponent extends Component{
  val abilities: Array[Entity] = new Array(4)
}
