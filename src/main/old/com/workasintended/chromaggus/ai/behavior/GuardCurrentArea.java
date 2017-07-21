package com.workasintended.chromaggus.ai.behavior;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 5/2/16.
 */
public class GuardCurrentArea extends LeafTask<Blackboard> {
    @Override
    public Status execute() {
        if(getStatus() != Status.RUNNING) {
            Blackboard b = getObject();
            Unit self = b.getSelf();
            b.setStationPosition(new Vector2(self.getX(Align.center), self.getY(Align.center)));

            System.out.println(String.format("marking current position as station: %s", b.getStationPosition()));
        }

        return Status.RUNNING;
    }

    @Override
    protected Task copyTo(Task task) {
        return task;
    }
}
