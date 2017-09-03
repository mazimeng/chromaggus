package com.workasintended.chromaggus.system

import com.badlogic.ashley.core.{ComponentMapper, Engine, EntitySystem}
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.scenes.scene2d.ui.{HorizontalGroup, Skin, TextButton}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.workasintended.chromaggus.Service
import com.workasintended.chromaggus.component.{AbilityCollectionComponent, AbilityComponent}
import com.workasintended.chromaggus.event.{Event, EventHandler}
import com.workasintended.chromaggus.event.Events.{CharacterSelectionChanged, TargetRequired}

class CharacterUiSystem(val ui: Stage) extends EntitySystem {
  val abilityCollectionComponent: ComponentMapper[AbilityCollectionComponent] = ComponentMapper.getFor(classOf[AbilityCollectionComponent])
  val abilityComponent: ComponentMapper[AbilityComponent] = ComponentMapper.getFor(classOf[AbilityComponent])

  val characterSelectedHandler = new EventHandler[CharacterSelectionChanged] {
    override def handle(arg: CharacterSelectionChanged): Unit = {
      println(s"character selected: ${arg.character.isDefined}")
      actionPanel.setVisible(arg.character.isDefined)

      if (arg.character.isDefined) {
        val abilityCollection = abilityCollectionComponent.get(arg.character.get)
        if (abilityCollection == null) return

        for (elem <- abilityButtons) {
          elem.setVisible(false)
        }
        var buttonIndex = 0
        for (elem <- abilityCollection.abilities) {
          val ac = abilityComponent.get(elem)
          if(ac != null && ac.isEquipped && buttonIndex < abilityButtons.length) {
            abilityButtons(buttonIndex).setText(ac.name)
            abilityButtons(buttonIndex).setVisible(true)
            buttonIndex += 1
          }
        }
      }
    }
  }
  val targetRequired = new Event[TargetRequired]()
  val skin: Skin = Service.assetManager.get("uiskin.json")
  val actionPanel = new HorizontalGroup()
  val abilityButtonSlots = 4
  val abilityButtons: Array[TextButton] = new Array[TextButton](abilityButtonSlots)

  override def addedToEngine(engine: Engine): Unit = {
    init()
  }

  def init(): Unit = {
    for (i <- 0 until abilityButtonSlots) {
      val button: TextButton = new TextButton("", skin)
      abilityButtons(i) = button
      button.addListener(new ClickListener() {
        override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
          val label = event.getListenerActor.asInstanceOf[TextButton].getText.toString
          targetRequired.fire(TargetRequired(label))
        }
      })

      actionPanel.setFillParent(true)
      actionPanel.setVisible(false)
      actionPanel.bottom().right()
      actionPanel.addActor(button)

      ui.addActor(actionPanel)
    }
  }
}
