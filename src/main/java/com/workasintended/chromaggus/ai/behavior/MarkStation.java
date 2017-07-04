package com.workasintended.chromaggus.ai.behavior;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 6/10/16.
 */
public class MarkStation extends LeafTask<Blackboard> {
    @Override
    public Status execute() {
        Blackboard b = getObject();
        Unit unit = b.getSelf();
        if (b.getStationPosition() == null) {
            b.setStationPosition(new Vector2(unit.getX(Align.center), unit.getY(Align.center)));
        }
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Blackboard> copyTo(Task<Blackboard> task) {
        return task;
    }
};

