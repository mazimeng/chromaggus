package com.workasintended.chromaggus.action;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 2/9/16.
 */
public class MoveToPositionAggressively extends MoveToPosition {
    private float alertRadius;

    public MoveToPositionAggressively(Vector2 targetPosition, float speed, float alertRadius) {
        super(targetPosition, speed);
        this.alertRadius = alertRadius;
    }

    @Override
    protected boolean completed(float delta) {
        boolean enemySpotted = spotEnemy();

        return super.completed(delta) && enemySpotted;
    }

    private boolean spotEnemy() {
        Stage stage = this.getActor().getStage();

        for (Actor a : stage.getActors()) {
            if(!(a instanceof Unit)) continue;

            Unit u = (Unit) a;
            if(u.combat==null) continue;

            return true;
        }

        return false;
    }
}
