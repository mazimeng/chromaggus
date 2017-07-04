package com.workasintended.chromaggus.ai.behavior;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 5/22/16.
 */
public class MoveToPosition extends LeafTask<Blackboard> {
    private float radius = 32f;
    private GetPosition getPosition;

    public MoveToPosition(GetPosition getPosition, float radius) {
        this.getPosition = getPosition;
        this.radius = radius;
    }

    @Override
    public Task.Status execute() {
        if(getStatus()!= Task.Status.RUNNING) {
            Unit self = getObject().getSelf();
            Vector2 position = getPosition.get(getObject());

            if(position != null) self.movement.moveToPosition(position, radius);
        }

        if(arrived()) {
            return Task.Status.SUCCEEDED;
        }

        return Task.Status.RUNNING;
    }

    private boolean arrived() {
        Unit self = getObject().getSelf();
        Vector2 position = getPosition.get(getObject());
        float distanceToTarget2 = position.dst2(self.getX(Align.center), self.getY(Align.center));

        return distanceToTarget2 <= radius*radius;
    }

    @Override
    protected Task copyTo(Task task) {
        return task;
    }

    @Override
    public Task<Blackboard> cloneTask() {
        return new MoveToPosition(this.getPosition, this.radius);
    }
}
