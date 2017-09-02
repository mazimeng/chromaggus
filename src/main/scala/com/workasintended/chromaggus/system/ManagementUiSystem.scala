package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent, Stage}
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.{ChangeListener, ClickListener}
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent
import com.workasintended.chromaggus.Service
import com.workasintended.chromaggus.component._
import com.workasintended.chromaggus.event.EventHandler
import com.workasintended.chromaggus.event.Events.FactionIncomeChanged

/**
  * Created by mazimeng on 7/30/17.
  */
class ManagementUiSystem(val ui: Stage) extends EntitySystem {
  val nameComponent: ComponentMapper[NameComponent] = ComponentMapper.getFor(classOf[NameComponent])
  val abilityCollectionComponent: ComponentMapper[AbilityCollectionComponent] = ComponentMapper.getFor(classOf[AbilityCollectionComponent])
  val abilityComponent: ComponentMapper[AbilityComponent] = ComponentMapper.getFor(classOf[AbilityComponent])
  val skin: Skin = Service.assetManager.get("uiskin.json")
  val factionComponent: ComponentMapper[FactionComponent] = ComponentMapper.getFor(classOf[FactionComponent])

  val incomeChangeHandler = new EventHandler[FactionIncomeChanged] {
    override def handle(arg: FactionIncomeChanged): Unit = {
      val faction = factionComponent.get(arg.faction)
      val ps: PlayerSystem = getEngine().getSystem(classOf[PlayerSystem])
      if (ps.faction == arg.faction) {
        goldIndicator.setText(s"gold: ${faction.gold}")
      }
    }
  }

  val characterList = new List[Entity](skin) {
    override def toString(obj: Entity): String = nameComponent.get(obj).toString()
  }

  val abilityList = new List[String](skin)
  val selectedFamily: Family = Family.all(classOf[SelectedComponent], classOf[ActorComponent], classOf[SelectableComponent]).get()
  val goldIndicator: Label = new Label("gold: ", skin)

  override def addedToEngine(engine: Engine): Unit = {
    initGui()
  }

  def fillCharacters(): Unit = {
    val ps: PlayerSystem = getEngine().getSystem(classOf[PlayerSystem])
    val characters = factionComponent.get(ps.faction).characters.toArray

    if (characters.length == 0) return

    val charArray = new com.badlogic.gdx.utils.Array[Entity](characters.length)
    for (i <- characters.indices) {
      charArray.add(characters(i))
    }
    characterList.clearItems()
    characterList.setItems(charArray)
    characterList.getSelection().clear()
    abilityList.clearItems()
  }

  def fillAbilities(): Unit = {
    if (characterList.getSelected == null) return

    val character = characterList.getSelected
    val acc = abilityCollectionComponent.get(character)

    val nameArray = new com.badlogic.gdx.utils.Array[String](acc.abilities.size)
    nameArray.add("none")
    for (elem <- acc.abilities) {
      nameArray.add(abilityComponent.get(elem).name)
    }

    abilityList.clearItems()
    abilityList.setItems(nameArray)
    //    abilityList.getSelection().clear()

    for (elem <- acc.abilities) {
      if (elem == null) {}
      else {
        val ability = abilityComponent.get(elem)
        if (ability.isEquipped) {
          abilityList.setSelected(ability.name)
        }
      }
    }
  }

  def initGui(): Unit = {
    val m = menu()
    characters(m)
    items(m)

    m.add(goldIndicator)
  }

  def menu(): Table = {
    val table = new Table(skin)
    table.top().left()
    table.setDebug(true, true)
    table.setFillParent(true)

    ui.addActor(table)
    table
  }

  def characters(menu: Table): Window = {
    val window = new Window("characters", skin)
    window.setDebug(true, true)
    window.setResizable(true)
    window.setSize(320f, 200f)
    window.setVisible(false)
    window.top().left()
    ui.addActor(window)

    val closeWindowButton = new Label("close", skin)

    closeWindowButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        window.setVisible(false)
      }
    })
    window.getTitleTable().add(closeWindowButton)

    characterList.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        println(s"characterList ${characterList.getSelected()}")
        val cs = getEngine.getSystem(classOf[ControlSystem])
        cs.select(characterList.getSelected)
        fillAbilities()
      }
    })

    {
      val scroll = new ScrollPane(characterList, skin)
      window.add(scroll).top().left()
    }
    {
      val scroll = new ScrollPane(abilityList, skin)
      window.add(scroll).top().left()

      abilityList.addListener(new ClickListener() {
        override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
          println(s"abilityList selected ${abilityList.getSelected()}")
          val character = characterList.getSelected
          val acc = abilityCollectionComponent.get(character)

          for (elem <- acc.abilities) {
            if (abilityList.getSelected == "none") {
              abilityComponent.get(elem).isEquipped = false
            }
            else if (abilityComponent.get(elem).name == abilityList.getSelected) {
              abilityComponent.get(elem).isEquipped = true
            }
          }
        }
      })
    }

    val weaponsButton = new TextButton("characters", skin)

    weaponsButton.addListener(new ChangeListener() {
      def changed(event: ChangeEvent, actor: Actor): Unit = {
        window.setVisible(!window.isVisible)

        if (!window.isVisible) return

        fillCharacters()
      }
    })

    menu.add(weaponsButton)

    window
  }

  def items(menu: Table): Window = {
    val window = new Window("item", skin)
    window.setDebug(true, true)
    window.setResizable(true)
    window.setSize(480, 480)
    window.setVisible(false)

    val closeWindowButton = new Label("close", skin)

    closeWindowButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        window.setVisible(!window.isVisible)
      }
    })

    window.getTitleTable().add(closeWindowButton)


    window.top().left()

    ui.addActor(window)

    val table = new Table(skin)
    window.add(new Label("ashkandi", skin))
    window.add(new Label("10-15", skin))
    window.row()
    window.add(new Label("one-hand sword", skin))
    window.add(new Label("2-5", skin))

    window.add(table)

    val weaponsButton = new TextButton("items", skin)

    weaponsButton.addListener(new ChangeListener() {
      def changed(event: ChangeEvent, actor: Actor): Unit = {
        window.setVisible(!window.isVisible)
      }
    })

    menu.add(weaponsButton)

    window
  }
}
