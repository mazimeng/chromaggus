package com.workasintended.chromaggus

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}

/**
  * Created by mazimeng on 7/22/17.
  */
object Factory {
  def makeSelectionAnimation(): Animation[TextureRegion] = {
    val itemTexture: Texture = Service.assetManager.get("spritesheet/selection.png")
    val selectionTextureRegions = TextureRegion.split(itemTexture, itemTexture.getWidth() / 2, itemTexture.getHeight / 1)
    val selectionAnimation = new Animation[TextureRegion](0.5f, selectionTextureRegions(0)(0), selectionTextureRegions(0)(1))

    selectionAnimation
  }
}
