package com.workasintended.chromaggus.unitcomponent;

import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 1/17/16.
 */
public abstract class UnitComponent {
    private Unit self;

    public UnitComponent(Unit self) {
        this.self = self;
    }

    public void update(float delta) {

    }

    public Unit getSelf() {
        return self;
    }

    public void setSelf(Unit self) {
        this.self = self;
    }
}
