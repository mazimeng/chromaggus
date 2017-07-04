package com.workasintended.chromaggus.unitcomponent;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.workasintended.chromaggus.Unit;
import com.workasintended.chromaggus.action.Develop;
import com.workasintended.chromaggus.action.MoveToUnit;

/**
 * Created by mazimeng on 1/17/16.
 */
public class DevelopmentComponent extends UnitComponent {

    public DevelopmentComponent(Unit self) {
        super(self);
    }

    @Override
    public void update(float delta) {
    }

    public void develop(Unit city) {
        if(city.city == null) return;
        Develop develop  = new Develop(city);

        SequenceAction sequenceAction = new SequenceAction(new MoveToUnit(city, getSelf().getSpeed(), 32), develop);
        getSelf().clearActions();
        getSelf().addAction(sequenceAction);
    }
}
