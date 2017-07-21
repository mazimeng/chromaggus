package com.workasintended.chromaggus.event;

import com.workasintended.chromaggus.EventName;
import com.workasintended.chromaggus.Unit;
import com.workasintended.chromaggus.event.order.UnitEvent;

/**
 * Created by mazimeng on 1/20/16.
 */
public class UnitSelectionEvent extends UnitEvent {
    public UnitSelectionEvent(Unit self, boolean selected) {
        super(self, selected? EventName.UNIT_SELECTED: EventName.UNIT_DESELECTED);
    }
}
