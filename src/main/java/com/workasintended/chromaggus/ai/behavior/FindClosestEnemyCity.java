package com.workasintended.chromaggus.ai.behavior;

import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 5/21/16.
 */
public class FindClosestEnemyCity extends FindClosestCity{
    @Override
    protected boolean filter(Unit city) {
        Blackboard b = getObject();
        return !b.getSelf().getFaction().isFriend(city.getFaction());
    }
}
