package com.workasintended.chromaggus.ai.behavior;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 5/2/16.
 */
public class TargetIsAThreat extends LeafTask<Blackboard> {
    private float radius = 160;

    @Override
    public Status execute() {
        final Blackboard b = getObject();
        Unit enemy = b.getTarget();
        boolean threat = false;

        if(enemy!=null) {
            Unit self = getObject().getSelf();
            float d2 = Vector2.dst2(enemy.getX(), enemy.getY(), self.getX(), self.getY());
            threat = d2 <= radius*radius;
            threat = threat && enemy.combat != null && enemy.getFaction() != self.getFaction() && enemy.combat.getHp()>0;
        }

        return threat? Status.SUCCEEDED: Status.FAILED;
    }

    @Override
    protected Task copyTo(Task task) {
        return task;
    }
}