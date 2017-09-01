package com.workasintended.chromaggus.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2

/**
  * Created by mazimeng on 7/22/17.
  */
class CameraComponent(val orthographicCamera: OrthographicCamera,
                      var zoom: Float,
                      var position: Vector2) extends Component {}