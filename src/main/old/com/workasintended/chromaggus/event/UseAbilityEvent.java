package com.workasintended.chromaggus.event;

import com.workasintended.chromaggus.Event;
import com.workasintended.chromaggus.EventName;
import com.workasintended.chromaggus.Unit;
import com.workasintended.chromaggus.ability.Ability;

/**
 * Created by mazimeng on 1/22/16.
 */
public class UseAbilityEvent extends Event {
    private Ability ability;
    private Unit target;

    public UseAbilityEvent(Ability ability, Unit target) {
        super(EventName.USE_ABILITY);
        this.ability = ability;
        this.target = target;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public Unit getTarget() {
        return target;
    }

    public void setTarget(Unit target) {
        this.target = target;
    }
}
