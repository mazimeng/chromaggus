package com.workasintended.chromaggus.event;

import com.badlogic.gdx.math.Vector2;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 11/7/15.
 */
public class MoveUnitArgument {
    private Unit unit;
    private Vector2 target;

    public MoveUnitArgument() {

    }

    public MoveUnitArgument(Unit unit, Vector2 target) {
        this.unit = unit;
        this.target = target;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Vector2 getTarget() {
        return target;
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }
}
