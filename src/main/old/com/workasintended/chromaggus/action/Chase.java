package com.workasintended.chromaggus.action;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

/**
 * Created by mazimeng on 8/1/15.
 */
public class Chase extends MoveToUnit {
    private float chaseRadius;
    private Vector2 initialPosition;

    public Chase(Actor targetUnit, float speed, float targetRadius, float chaseRadius) {
        super(targetUnit, speed, targetRadius);
        this.chaseRadius = chaseRadius;
    }

    @Override
    protected void begin() {
        super.begin();
        this.initialPosition = new Vector2(getActor().getX(Align.center), getActor().getY(Align.center));
    }

    @Override
    public boolean act(float delta) {
        boolean farEnough = farEnough();
        return farEnough || super.act(delta);
    }

    protected boolean farEnough() {
        Vector2 currentPosition = new Vector2(getActor().getX(Align.center), getActor().getY(Align.center));
        float dst = currentPosition.dst2(initialPosition);
        return dst >= chaseRadius*chaseRadius;
    }
}