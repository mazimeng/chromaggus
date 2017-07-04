package com.workasintended.chromaggus.event.order;

import com.workasintended.chromaggus.EventName;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 1/19/16.
 */
public class FollowUnitEvent extends UnitEvent {
    private Unit target;
    public FollowUnitEvent(Unit self, Unit target) {
        super(self, EventName.FOLLOW_UNIT);
        this.target = target;
    }

    public Unit getTarget() {
        return target;
    }

    public void setTarget(Unit target) {
        this.target = target;
    }
}
