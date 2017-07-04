package com.workasintended.chromaggus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by mazimeng on 6/29/15.
 */
public class Cursor extends Actor {
    Texture cursorTexture;
    public Cursor(Texture cursorTexture) {
        this.cursorTexture = cursorTexture;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(cursorTexture, getX(), getY());
    }
}
