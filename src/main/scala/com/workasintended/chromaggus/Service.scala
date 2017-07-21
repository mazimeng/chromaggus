package com.workasintended.chromaggus

import com.badlogic.gdx.assets.AssetManager
import com.workasintended.chromaggus.event.EventQueue

object Service {
  var eventQueue: EventQueue = null
  var assetManager = null

  def eventQueue: Nothing = eventQueue

  def setEventQueue(eventQueue: Nothing) {
    Service.eventQueue = eventQueue
  }

  def assetManager: Nothing = assetManager

  def setAssetManager(assetManager: Nothing) {
    Service.assetManager = assetManager
  }
}
