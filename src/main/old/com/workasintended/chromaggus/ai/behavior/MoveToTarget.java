package com.workasintended.chromaggus.ai.behavior;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 4/28/16.
 */
public class MoveToTarget extends LeafTask<Blackboard> {
    private float radius = 32f;
    public MoveToTarget() {
    }

    @Override
    public Status execute() {
        if(getStatus()!=Status.RUNNING) {
            Unit self = getObject().getSelf();
            Unit target = getObject().getTarget();
            self.movement.moveToPosition(new Vector2(target.getX(), target.getY()), radius);
        }

        if(arrived()) {
            getObject().setTarget(null);
            return Status.SUCCEEDED;
        }

        return Status.RUNNING;
    }

    private boolean arrived() {
        Unit self = getObject().getSelf();
        Unit target = getObject().getTarget();
        Vector2 position = new Vector2(self.getX(Align.center), self.getY(Align.center));
        float distanceToTarget2 = position.dst2(target.getX(), target.getY());

        return distanceToTarget2 <= radius*radius;
    }

    @Override
    protected Task copyTo(Task task) {
        return task;
    }
}
