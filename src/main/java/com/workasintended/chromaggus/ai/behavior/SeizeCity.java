package com.workasintended.chromaggus.ai.behavior;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 4/28/16.
 */
public class SeizeCity extends LeafTask<Blackboard> {
    public SeizeCity() {
//        setGuard(new TargetExists());
    }

    @Override
    public Status execute() {
        if(getStatus()!=Status.RUNNING) {
            Unit self = getObject().getSelf();
            Unit target = getObject().getTarget();
            self.combat.seize(target);
        }

        return Status.RUNNING;
    }

    @Override
    protected Task copyTo(Task task) {
        return task;
    }
}
