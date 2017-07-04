package com.workasintended.chromaggus.ai.behavior;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 4/28/16.
 */
public class TargetAlive extends LeafTask<Blackboard> {
    @Override
    public Status execute() {
        Unit target = getObject().getTarget();

        return target.combat.getHp()>0?
                Status.SUCCEEDED: Status.FAILED;
    }

    @Override
    protected Task copyTo(Task task) {
        return task;
    }
}
