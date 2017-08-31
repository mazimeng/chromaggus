package com.workasintended.chromaggus.event

class Event(val name: String) {

  def canEqual(other: Any): Boolean = other.isInstanceOf[Event]

  override def equals(other: Any): Boolean = other match {
    case that: Event =>
      (that canEqual this) &&
        name == that.name
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(name)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
