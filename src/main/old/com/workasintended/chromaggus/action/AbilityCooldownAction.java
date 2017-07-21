package com.workasintended.chromaggus.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.workasintended.chromaggus.ability.Ability;

/**
 * Created by mazimeng on 5/4/16.
 */
public class AbilityCooldownAction extends Action {
    private Ability ability;
    private boolean init = false;

    public AbilityCooldownAction(Ability ability) {
        this.ability = ability;
    }

    @Override
    public boolean act(float delta) {
        ability.getCooldown();
        return false;
    }


}
