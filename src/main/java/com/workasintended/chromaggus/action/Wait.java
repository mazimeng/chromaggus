package com.workasintended.chromaggus.action;

import com.badlogic.gdx.scenes.scene2d.Action;

/**
 * Created by mazimeng on 1/26/16.
 */
public class Wait extends Action {
    private float wait = 0;
    private float waited = 0;

    public Wait(float wait) {
        this.wait = wait;
    }
    @Override
    public boolean act(float delta) {
        waited = Math.min(waited+delta, wait);
        boolean done = waited==wait;
        if(done) done();
        return done;
    }

    protected void done() {

    }
}
