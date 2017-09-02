package com.workasintended.chromaggus.system

import com.badlogic.ashley.core.{Engine, EntitySystem}
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.{HorizontalGroup, Skin, TextButton}
import com.workasintended.chromaggus.Service
import com.workasintended.chromaggus.event.{Event, EventHandler}
import com.workasintended.chromaggus.event.Events.{CharacterSelectionChanged, TargetRequired}

class CharacterUiSystem(val ui: Stage) extends EntitySystem {
  val characterSelectedHandler = new EventHandler[CharacterSelectionChanged] {
    override def handle(arg: CharacterSelectionChanged): Unit = {
      actionPanel.setVisible(arg.character.isDefined)
      println(s"character selected: ${arg.character.isDefined}")
    }
  }
  val targetRequired = new Event[TargetRequired]()
  val skin: Skin = Service.assetManager.get("uiskin.json")
  val actionPanel = new HorizontalGroup()

  override def addedToEngine(engine: Engine): Unit = {
    init()
  }

  def init(): Unit = {
    val button = new TextButton("siege", skin)
    actionPanel.setFillParent(true)
    actionPanel.setVisible(false)
    actionPanel.bottom().right()
    actionPanel.addActor(button)

    ui.addActor(actionPanel)
  }
}
