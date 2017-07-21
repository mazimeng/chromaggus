package com.workasintended.chromaggus.event.order;

import com.workasintended.chromaggus.Event;
import com.workasintended.chromaggus.EventName;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 1/19/16.
 */
public abstract class UnitEvent extends Event {
    private Unit self;

    public UnitEvent(Unit self, EventName eventName) {
        super(eventName);
        this.self = self;
    }

    public Unit getUnit() {
        return self;
    }

    public void setUnit(Unit self) {
        this.self = self;
    }
}
