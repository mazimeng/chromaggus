package com.workasintended.chromaggus.unitcomponent;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 5/4/16.
 */
public class AbilityComponent extends UnitComponent{
    private SequenceAction abilityAction = new SequenceAction();

    public AbilityComponent(Unit self) {
        super(self);
    }

    public void useAbility(Action ability) {
        Array<Action> actions = getSelf().getActions();
        if(!actions.contains(abilityAction, true)) actions.add(abilityAction);

        abilityAction.addAction(ability);
    }

    public void reset() {
        abilityAction.reset();
    }
}
