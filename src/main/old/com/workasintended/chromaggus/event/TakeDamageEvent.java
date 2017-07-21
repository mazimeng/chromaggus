package com.workasintended.chromaggus.event;

import com.workasintended.chromaggus.EventName;
import com.workasintended.chromaggus.Unit;
import com.workasintended.chromaggus.event.order.UnitEvent;

/**
 * Created by mazimeng on 1/23/16.
 */
public class TakeDamageEvent extends UnitEvent {
    public TakeDamageEvent(Unit self) {
        super(self, EventName.TAKE_DAMAGE);
    }
}
