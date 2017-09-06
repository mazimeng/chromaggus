package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.{Component, Entity}

class CityComponent extends Component {
  var income: Long = 0L
  var population: Long = 0L
  var management: Float = 0f
}
