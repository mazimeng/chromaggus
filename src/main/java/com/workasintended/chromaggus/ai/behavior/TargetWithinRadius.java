package com.workasintended.chromaggus.ai.behavior;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 4/28/16.
 */
public class TargetWithinRadius extends LeafTask<Blackboard> {
    private float radius = 32;

    public TargetWithinRadius(float radius) {
        this.radius = radius;
    }

    public TargetWithinRadius() {
    }

    @Override
    public Status execute() {
        Unit target = getObject().getTarget();
        Unit self = getObject().getSelf();

        float d = Vector2.dst2(target.getX(), target.getY(), self.getX(), self.getY());

        return d <= getRadius()*getRadius()?
                Status.SUCCEEDED: Status.FAILED;
    }

    @Override
    protected Task<Blackboard> copyTo(Task<Blackboard> task) {
        return task;
    }

    protected float getRadius() {
        return radius;
    }
}
