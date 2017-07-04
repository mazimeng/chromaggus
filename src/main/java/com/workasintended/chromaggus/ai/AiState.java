package com.workasintended.chromaggus.ai;

import com.workasintended.chromaggus.Event;
import com.workasintended.chromaggus.EventHandler;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 8/14/15.
 */
public abstract class AiState implements EventHandler {
    private Unit unit;
    public AiState(Unit unit) {
        this.unit = unit;
    }

    public void update(float delta) {
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public abstract void onEnter();

    @Override
    public void handle(Event event) {

    }
}
