package com.workasintended.chromaggus.event.order;

import com.badlogic.gdx.math.Vector2;
import com.workasintended.chromaggus.EventName;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 1/19/16.
 */
public class MoveToPositionEvent extends UnitEvent {
    private Vector2 position;
    public MoveToPositionEvent(Unit self, Vector2 position) {
        super(self, EventName.MOVE_TO_POSITION);
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
