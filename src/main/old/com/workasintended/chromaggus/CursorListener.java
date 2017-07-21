package com.workasintended.chromaggus;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by mazimeng on 6/29/15.
 */
public class CursorListener extends InputListener {
    private Cursor cursor;

    public CursorListener(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        cursor.setPosition((int)(x/32)*32, (int)(y/32)*32);
        return false;
    }
}
