package com.workasintended.chromaggus.ai.behavior;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 4/28/16.
 */
public class StopEverything extends LeafTask<Blackboard> {
    public StopEverything() {
//        setGuard(new TargetExists());
    }

    @Override
    public Status execute() {
        Unit self = getObject().getSelf();
        self.clearActions();

        getObject().setTarget(null);

        return Status.SUCCEEDED;
    }

    @Override
    protected Task copyTo(Task task) {
        return task;
    }
}