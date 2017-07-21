package com.workasintended.chromaggus.event;

import com.workasintended.chromaggus.Event;
import com.workasintended.chromaggus.EventName;
import com.workasintended.chromaggus.Unit;

import java.util.List;

/**
 * Created by mazimeng on 8/8/15.
 */
public class SelectionCompleted extends Event {
    public SelectionCompleted(List<Unit> units) {
        super(EventName.SELECTION_COMPLETED, units);
    }

    public List<Unit> getUnits() {
        return (List<Unit>)this.argument;
    }
}
