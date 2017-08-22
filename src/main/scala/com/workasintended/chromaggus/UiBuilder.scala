package com.workasintended.chromaggus

import com.badlogic.gdx.scenes.scene2d.{Actor, Stage}
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent

class UiBuilder(val ui: Stage) {
  val skin: Skin = Service.assetManager.get("uiskin.json")

  def initGui(): Unit = {
    //    ui.setViewport(viewport)
    val table = new Table(skin)
    table.setDebug(true)
    table.setFillParent(true)
    table.right().top()


    val list = new com.badlogic.gdx.scenes.scene2d.ui.List[TextButton](skin)

    list.addListener(new ChangeListener() {
      def changed(event: ChangeEvent, actor: Actor): Unit = {
        val theList = actor.asInstanceOf[com.badlogic.gdx.scenes.scene2d.ui.List[String]]
        println(s"list changed: ${theList.getSelected}")
      }
    })

    val button = new TextButton("Click me!", skin)

    // Add a listener to the button. ChangeListener is fired when the button's checked state changes, eg when clicked,
    // Button#setChecked() is called, via a key press, etc. If the event.cancel() is called, the checked state will be reverted.
    // ClickListener could have been used, but would only fire when clicked. Also, canceling a ClickListener event won't
    // revert the checked state.
    button.addListener(new ChangeListener() {
      def changed(event: ChangeEvent, actor: Actor): Unit = {
        System.out.println("Clicked! Is checked: " + button.isChecked)
        button.setText("Good job!")
      }
    })

    table.add(button)

    val container = new Container[TextButton]()
    container.setFillParent(true)
    container.setDebug(true, true)
    //    container.top().right()
    //    ui.addActor(container)

    container.setActor(button)

    val m = menu()


    weapons(m)
    inventory(m)

    m.row().bottom().left().expand()
    m.add(new Label("hehe", skin))
  }

  def menu(): Table = {
    val table = new Table(skin)
    table.top().left()
    table.setDebug(true, true)
    table.setFillParent(true)

    ui.addActor(table)
    table
  }

  def weapons(menu: Table): Window = {
    val window = new Window("weapons", skin)
    window.setDebug(true, true)
    window.setResizable(true)
    window.setSize(320f, 200f)
    window.setVisible(false)
    window.top().left()

    ui.addActor(window)

    val table = new Table(skin)
    window.add(new Label("ashkandi", skin))
    window.add(new Label("10-15", skin))
    window.add(new Label("25%", skin))
    window.add(new TextButton("craft", skin))
    window.row()
    window.add(new Label("one-hand sword", skin))
    window.add(new Label("2-5", skin))
    window.add(new Label("30%", skin))
    window.add(new TextButton("craft", skin))

    window.add(table)

    val weaponsButton = new TextButton("weapons", skin)

    weaponsButton.addListener(new ChangeListener() {
      def changed(event: ChangeEvent, actor: Actor): Unit = {
        window.setVisible(!window.isVisible)
      }
    })

    menu.add(weaponsButton)

    window
  }

  def inventory(menu: Table): Window = {
    val window = new Window("inventory", skin)
    window.setDebug(true, true)
    window.setResizable(true)
    window.setSize(320f, 200f)
    window.setVisible(false)
    window.getTitleTable().add(new Label("close", skin))

    window.top().left()

    ui.addActor(window)

    val table = new Table(skin)
    window.add(new Label("ashkandi", skin))
    window.add(new Label("10-15", skin))
    window.row()
    window.add(new Label("one-hand sword", skin))
    window.add(new Label("2-5", skin))

    window.add(table)

    val weaponsButton = new TextButton("inventory", skin)

    weaponsButton.addListener(new ChangeListener() {
      def changed(event: ChangeEvent, actor: Actor): Unit = {
        window.setVisible(!window.isVisible)
      }
    })

    menu.add(weaponsButton)

    window
  }
}
