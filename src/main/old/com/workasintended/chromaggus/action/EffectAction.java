package com.workasintended.chromaggus.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.workasintended.chromaggus.ability.Ability;

/**
 * Created by mazimeng on 1/23/16.
 */
public class EffectAction extends Action {
    private Ability ability;

    public EffectAction(Ability ability) {
        this.ability = ability;
    }

    @Override
    public boolean act(float delta) {
        ability.effect();

        return true;
    }
}
