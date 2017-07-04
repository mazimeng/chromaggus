package com.workasintended.chromaggus.event;

import com.workasintended.chromaggus.EventName;
import com.workasintended.chromaggus.Unit;
import com.workasintended.chromaggus.event.order.UnitEvent;

/**
 * Created by mazimeng on 1/19/16.
 */
public class DevelopCityEvent extends UnitEvent {
    private Unit city;

    public DevelopCityEvent(Unit self, Unit city) {
        super(self, EventName.DEVELOP_CITY);
        this.city = city;
    }

    public Unit getCity() {
        return city;
    }

    public void setCity(Unit city) {
        this.city = city;
    }
}
