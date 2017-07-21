package com.workasintended.chromaggus

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager

class ChromaggusGame extends Game {

  override def create(): scala.Unit = {
    Service.setEventQueue(new DefaultEventQueue)
    Service.setAssetManager(new AssetManager)

    val screen = new GameScreen()
    this.setScreen(screen)
  }


}
