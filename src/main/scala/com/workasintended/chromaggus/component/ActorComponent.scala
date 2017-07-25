package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.{Component, Entity}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Animation, Batch, TextureRegion}
import com.badlogic.gdx.scenes.scene2d.{Actor, Touchable}

/**
  * Created by mazimeng on 7/22/17.
  */
class ActorComponent(val actor: Actor) extends Component {}