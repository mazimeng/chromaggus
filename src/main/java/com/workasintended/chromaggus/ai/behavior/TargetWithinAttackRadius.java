package com.workasintended.chromaggus.ai.behavior;

import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 4/29/16.
 */
public class TargetWithinAttackRadius extends TargetWithinRadius {
    @Override
    protected float getRadius() {
        Unit self = getObject().getSelf();

        if(self.combat==null) return 0;

        return self.combat.getPrimaryAbility().getCastRange();
    }
}
