package com.workasintended.chromaggus.system

import com.badlogic.ashley.core._
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent, Stage}
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.{ChangeListener, ClickListener}
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent
import com.badlogic.gdx.utils.Align
import com.workasintended.chromaggus.Service
import com.workasintended.chromaggus.component.NameComponent

/**
  * Created by mazimeng on 7/30/17.
  */
class AbilityUiSystem(val ui: Stage) extends EntitySystem {
  val nameComponent: ComponentMapper[NameComponent] = ComponentMapper.getFor(classOf[NameComponent])
  val skin: Skin = Service.assetManager.get("uiskin.json")
  val characterList = new List[Entity](skin){
    override def toString(obj: Entity): String = nameComponent.get(obj).toString()
  }

  override def addedToEngine(engine: Engine): Unit = {
    initGui()
  }

  def setCharacters(characters: Array[Entity]): Unit = {
    if(characters.length == 0) return

    val charArray = new com.badlogic.gdx.utils.Array[Entity](characters.length)
    for (i <- characters.indices) {
      charArray.add(characters(i))
    }

    characterList.setItems(charArray)
  }

  def initGui(): Unit = {
    val m = menu()
    characters(m)
    items(m)
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
        println(s"${characterList.getSelected()}")
      }
    })

    characterList.getSelection().clear()

    characterList.addListener(new ChangeListener(){
      override def changed(event: ChangeEvent, actor: Actor): Unit = {
        println("char list changed")
      }
    })

    val scroll = new ScrollPane(characterList, skin)
    window.add(scroll).top().left().expandY()

    val abilitySlots = 2
    val vg = new VerticalGroup()
    vg.align(Align.top & Align.left)

    for(_ <- 0 until abilitySlots) {
      val abilityList = new SelectBox[String](skin)
      abilityList.setItems("none", "fireball", "mortal strike", "shield wall")
      vg.addActor(abilityList)

      abilityList.addListener(new ChangeListener() {
        def changed(event: ChangeEvent, actor: Actor): Unit = {
          println(s"selected ${abilityList.getSelected()}")
        }
      })
    }

    window.add(vg).top().left().expandX()

    val weaponsButton = new TextButton("characters", skin)

    weaponsButton.addListener(new ChangeListener() {
      def changed(event: ChangeEvent, actor: Actor): Unit = {
        window.setVisible(!window.isVisible)

        if(!window.isVisible) return


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
