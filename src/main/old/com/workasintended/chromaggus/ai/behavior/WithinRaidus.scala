package com.workasintended.chromaggus.ai.behavior

import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.math.Vector2

class WithinRadius(var getPosition: GetPosition, val radius: Float) extends LeafTask[Blackboard] {
  override def execute: Task.Status = {
    val self = getObject.getSelf
    val station = getPosition.get(getObject)
    val d2 = Vector2.dst2(station.x, station.y, self.getX, self.getY)
    println(s"checking radius, ${d2}<=${radius*radius} ?, station(${station}), current(${self.getX}, ${self.getY})")
    if (d2 <= radius * radius) Status.SUCCEEDED
    else Status.FAILED
  }

  override protected def copyTo(task: Task[Blackboard]): Task[Blackboard] = task

  override def cloneTask = new WithinRadius(this.getPosition, this.radius)
}
