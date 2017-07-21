package com.workasintended.chromaggus.action;

import com.badlogic.gdx.scenes.scene2d.Action;

/**
 * Created by mazimeng on 1/26/16.
 */
public class Do extends Action {
    private Runnable runnable;

    public Do(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public boolean act(float delta) {
        if(runnable!=null) runnable.run();
        return true;
    }
}
