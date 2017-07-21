package com.workasintended.chromaggus.ai.behavior;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

/**
 * Created by mazimeng on 4/28/16.
 */
public class TargetExists extends LeafTask<Blackboard> {
    @Override
    public Status execute() {
        return getObject().getTarget() != null?
                Status.SUCCEEDED: Status.FAILED;
    }

    @Override
    protected Task copyTo(Task task) {
        return task;
    }
}
