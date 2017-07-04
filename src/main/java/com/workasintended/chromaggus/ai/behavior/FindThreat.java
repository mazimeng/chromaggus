package com.workasintended.chromaggus.ai.behavior;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Predicate;
import com.workasintended.chromaggus.Unit;
import com.workasintended.chromaggus.WorldStage;

/**
 * Created by mazimeng on 4/28/16.
 */
public class FindThreat extends LeafTask<Blackboard> {
    private float radius = 128;
    @Override
    public Status execute() {
        final Blackboard b = getObject();
        WorldStage worldStage = b.getWorldStage();
        Unit target = b.getTarget();

        Unit enemy = null;
        if(target == null || target.getFaction()==b.getSelf().getFaction()) {
            enemy = b.findNearest(worldStage.getUnits(), new Predicate<Unit>() {
                @Override
                public boolean evaluate(Unit u) {
                    return u.combat!=null
                            && u.getFaction()!=b.getSelf().getFaction()
                            && u.combat.getHp()>0;
                }
            });
        }
        else {
            enemy = target;
        }

        if(enemy!=null) {
            Unit self = getObject().getSelf();
            float d2 = Vector2.dst2(enemy.getX(), enemy.getY(), self.getX(), self.getY());
            if(d2 <= radius*radius) {
                b.setTarget(enemy);
                return Status.SUCCEEDED;
            }
        }

        b.setTarget(null);
        return Status.FAILED;
    }

    @Override
    protected Task copyTo(Task task) {
        return task;
    }
}
