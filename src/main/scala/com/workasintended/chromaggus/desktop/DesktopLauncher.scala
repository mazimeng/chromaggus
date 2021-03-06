package com.workasintended.chromaggus.desktop

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import com.workasintended.chromaggus.ChromaggusGame

object DesktopLauncher {
	def main (args: Array[String]): scala.Unit = {
		val config = new LwjglApplicationConfiguration
		config.width = 1280
		config.height = 720

//		val gameConfiguration = new DesktopGameConfiguration

		val chromaggusGame = new ChromaggusGame()
		new LwjglApplication(chromaggusGame, config)
	}
}
