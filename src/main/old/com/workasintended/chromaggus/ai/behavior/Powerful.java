package com.workasintended.chromaggus.ai.behavior;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

import java.util.Random;

/**
 * Created by mazimeng on 4/28/16.
 */
public class Powerful extends LeafTask<Blackboard> {
    private Random random = new Random();
    public Powerful() {
    }

    @Override
    public Status execute() {
        float next = random.nextFloat();

        System.out.println("feeling powerful: "+next);

        if(next < 0.2f) return Status.SUCCEEDED;

        return Status.FAILED;
    }

    @Override
    protected Task copyTo(Task task) {
        return task;
    }
}
