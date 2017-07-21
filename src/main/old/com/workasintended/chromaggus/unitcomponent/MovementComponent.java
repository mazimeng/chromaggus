package com.workasintended.chromaggus.unitcomponent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.workasintended.chromaggus.InterruptionActorEvent;
import com.workasintended.chromaggus.Unit;
import com.workasintended.chromaggus.action.MoveIntoRadius;
import com.workasintended.chromaggus.action.MoveToPosition;
import com.workasintended.chromaggus.action.MoveToUnit;

/**
 * Created by mazimeng on 7/18/15.
 */
public class MovementComponent extends UnitComponent {
    public MovementComponent(Unit self) {
        super(self);
    }

    public void moveToPosition(Vector2 position) {
        Action action = new MoveToPosition(position, getSelf().getSpeed());
        getSelf().clearActions();
        getSelf().addAction(action);

        getSelf().fire(new InterruptionActorEvent());
    }
    public void moveToPosition(Vector2 position, float radius) {
        Action action = new MoveIntoRadius(position, getSelf().getSpeed(), radius);
        getSelf().clearActions();
        getSelf().addAction(action);
    }
    public void followUnit(Unit target) {
        MoveToUnit moveToUnit = new MoveToUnit(target, getSelf().getSpeed(), 32);
        RepeatAction repeatAction = new RepeatAction();
        repeatAction.setCount(RepeatAction.FOREVER);
        repeatAction.setAction(moveToUnit);
        getSelf().clearActions();
        getSelf().addAction(repeatAction);
    }
}
