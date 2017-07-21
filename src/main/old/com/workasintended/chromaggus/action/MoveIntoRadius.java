package com.workasintended.chromaggus.action;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 1/22/16.
 */
public class MoveIntoRadius extends MoveToPosition {
    private float radius;

    public MoveIntoRadius(Vector2 targetPosition, float speed, float radius) {
        super(targetPosition, speed);
        this.radius = radius;
    }

    @Override
    protected boolean completed(float delta) {
        Unit unit = (Unit)getActor();
        Vector2 position = new Vector2(unit.getX(Align.center), unit.getY(Align.center));
        float distanceToTarget2 = position.dst2(getTargetPosition().x, getTargetPosition().y);

        return distanceToTarget2 <= radius*radius;
    }
}
