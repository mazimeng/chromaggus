package com.workasintended.chromaggus.ai.behavior;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 4/28/16.
 */
public class TargetIsDead extends LeafTask<Blackboard> {
    public TargetIsDead() {
    }

    @Override
    public Status execute() {
        Unit target = getObject().getTarget();
        if(target == null) return Status.SUCCEEDED;
        if(target.dead()) return Status.SUCCEEDED;

        return Status.FAILED;
    }

    @Override
    protected Task copyTo(Task task) {
        return task;
    }
}
