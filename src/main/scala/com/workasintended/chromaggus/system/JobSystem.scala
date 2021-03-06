package com.workasintended.chromaggus.system

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.workasintended.chromaggus.component.{DeadComponent, JobComponent}

/**
  * Created by mazimeng on 7/26/17.
  */
class JobSystem(family: Family) extends IteratingSystem(family) {
  private val jobComponentMapper = ComponentMapper.getFor(classOf[JobComponent])

  def this() {
    this(Family.all(classOf[JobComponent]).exclude(classOf[DeadComponent]).get())
  }

  override def processEntity(entity: Entity, v: Float): scala.Unit = {
    val jobComponent = jobComponentMapper.get(entity)

    if(jobComponent.job.done) {
      entity.remove(classOf[JobComponent])
      return
    }

    jobComponent.job.update(v)
  }
}
