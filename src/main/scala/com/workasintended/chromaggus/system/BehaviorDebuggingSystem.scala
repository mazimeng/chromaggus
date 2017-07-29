package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.{Container, Table}
import com.workasintended.chromaggus.Blackboard
import com.workasintended.chromaggus.component.{BehaviorComponent, BehaviorDebuggerComponent, SelectedComponent}

/**
  * Created by mazimeng on 7/29/17.
  */
class BehaviorDebuggingSystem(val ui: Stage) extends EntitySystem {
  val debuggerFamily: Family = Family.all(classOf[BehaviorComponent], classOf[SelectedComponent], classOf[BehaviorDebuggerComponent[Blackboard]]).get()
  val behaviorComponent: ComponentMapper[BehaviorComponent] = ComponentMapper.getFor(classOf[BehaviorComponent])
  val behaviorDebuggerComponent: ComponentMapper[BehaviorDebuggerComponent[Blackboard]] =
    ComponentMapper.getFor(classOf[BehaviorDebuggerComponent[Blackboard]])

  val debuggerContainer = new Container[Table]()
  debuggerContainer.setFillParent(true)
  debuggerContainer.setDebug(true, true)
  debuggerContainer.top().right()

  override def addedToEngine(engine: Engine) {
    super.addedToEngine(engine)
    ui.addActor(debuggerContainer)

    engine.addEntityListener(debuggerFamily, new EntityListener() {
      override def entityAdded(entity: Entity): scala.Unit = {
        val component = behaviorDebuggerComponent.get(entity)
        debuggerContainer.setActor(component.treeViewer)
        println("behavior debugger added")
      }

      override def entityRemoved(entity: Entity): scala.Unit = {
        val component = behaviorDebuggerComponent.get(entity)
        debuggerContainer.removeActor(component.treeViewer)
        println("behavior debugger removed")
      }
    })
  }
}
