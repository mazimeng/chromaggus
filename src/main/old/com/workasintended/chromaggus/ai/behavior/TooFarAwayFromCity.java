package com.workasintended.chromaggus.ai.behavior;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Predicate;
import com.workasintended.chromaggus.Unit;
import com.workasintended.chromaggus.WorldStage;

/**
 * Created by mazimeng on 5/21/16.
 */
public class TooFarAwayFromCity extends LeafTask<Blackboard> {
    private float radius = 128;

    public TooFarAwayFromCity(float radius) {
        this.radius = radius;
    }

    public TooFarAwayFromCity() {
    }

    @Override
    public Status execute() {
        final Blackboard b = this.getObject();
        Unit self = getObject().getSelf();
        WorldStage worldStage = self.getWorld();

        Unit target = b.findNearest(worldStage.getUnits(), new Predicate<Unit>() {
            @Override
            public boolean evaluate(Unit u) {
                return u.city!=null && u.getFaction()==b.getSelf().getFaction();
            }
        });

        if(target == null) return Status.SUCCEEDED;

        float d = Vector2.dst2(target.getX(), target.getY(), self.getX(), self.getY());

        return d > getRadius()*getRadius()?
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
