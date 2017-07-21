package com.workasintended.chromaggus.action;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;

/**
 * Created by mazimeng on 1/23/16.
 * TODO: this isnt finished yet
 */
public class ShakeCamera extends Action {
    private OrthographicCamera camera;
    private Vector2 originPosition;
    private float time = 0f;
    private float frequency = (float)Math.PI;

    public ShakeCamera(OrthographicCamera camera) {
        this.camera = camera;
        originPosition = new Vector2(camera.position.x, camera.position.y);
    }

    @Override
    public boolean act(float delta) {
        if(time<=1) {
            float dst = (float)Math.cos(16*Math.PI*time);
            this.camera.position.x += dst;
            time = Math.min(time+delta, 1);
            return false;
        }
        return true;
    }
}
