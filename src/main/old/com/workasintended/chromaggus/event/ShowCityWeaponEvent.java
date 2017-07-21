package com.workasintended.chromaggus.event;

import com.workasintended.chromaggus.Event;
import com.workasintended.chromaggus.EventName;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 1/30/16.
 */
public class ShowCityWeaponEvent extends Event {
    private Unit city;
    private boolean show;
    public ShowCityWeaponEvent(Unit city, boolean show) {
        super(EventName.SHOW_CITY_WEAPON);
        this.city = city;
        this.show = show;
    }

    public Unit getCity() {
        return city;
    }

    public boolean isShow() {
        return show;
    }
}
