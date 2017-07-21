package com.workasintended.chromaggus.ability;

import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 3/31/16.
 */
public class Seize extends OffensiveAbilityAdaptor {
    public Seize(Unit user, Unit target) {
        super(user, target);
        setCastingTime(10f);
        setCastRange(16f);
        setCooldown(0f);
        setCooldownProgress(0f);
    }

    @Override
    public Ability clone() {
        return null;
    }

    @Override
    public void effect() {
        super.effect();
        getTarget().setFaction(getUser().getFaction());
        System.out.println("THIS CITY BELONGS TO ME NOW !");
    }

    @Override
    protected void useAbility() {
        super.useAbility();
        System.out.println("seize useAbility");
    }
}
