package com.workasintended.chromaggus

import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent, Stage}
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.{ChangeListener, ClickListener}
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent

class UiBuilder(val ui: Stage) {
  val skin: Skin = Service.assetManager.get("uiskin.json")

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
        window.setVisible(!window.isVisible)
      }
    })
    window.getTitleTable().add(closeWindowButton)

    val characterList = new List[String](skin)
    characterList.setItems("thrall", "arthas menethil", "illidan stormrage", "varian wrynn", "Acheras the Custodian", "Acridostrasz", "Adric")
    characterList.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        println(s"${characterList.getSelected()}")
      }
    })

    val scroll = new ScrollPane(characterList, skin)
    window.add(scroll).top().left().expandY()

    val weaponsButton = new TextButton("characters", skin)

    weaponsButton.addListener(new ChangeListener() {
      def changed(event: ChangeEvent, actor: Actor): Unit = {
        window.setVisible(!window.isVisible)
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
