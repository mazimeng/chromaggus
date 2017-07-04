package com.workasintended.chromaggus.event;

import com.workasintended.chromaggus.EventName;
import com.workasintended.chromaggus.Unit;
import com.workasintended.chromaggus.event.order.UnitEvent;

/**
 * Created by mazimeng on 1/21/16.
 */
public class GainGoldEvent extends UnitEvent {
    private float gold;

    public GainGoldEvent(Unit self, float goldGained) {
        super(self, EventName.GAIN_GOLD);
        this.gold = goldGained;
    }

    public float getGold() {
        return gold;
    }
}
