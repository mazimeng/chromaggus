package com.workasintended.chromaggus.target

import com.badlogic.ashley.core.Entity
import scala.collection.immutable.Seq

/**
  * Created by mazimeng on 7/27/17.
  */
trait TargetSelection {
  def targets(): Seq[Entity]
  def any(): Boolean
}
