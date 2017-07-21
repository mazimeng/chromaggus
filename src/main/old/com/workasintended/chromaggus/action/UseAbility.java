package com.workasintended.chromaggus.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.workasintended.chromaggus.ability.Ability;

/**
 * Created by mazimeng on 1/22/16.
 */
public class UseAbility extends Action {
    private Ability ability;
    private boolean began = false;

    public UseAbility(Ability ability) {
        this.ability = ability;
    }
    @Override
    public boolean act(float delta) {
        if(ability.stop()) return true;
        if(!began) begin();
        if(ability.inRange() || ability.isCasting()) {
            if(ability.cast(delta)) {
                ability.use();
                finish();
                return true;
            }
            else {
                return false;
            }
        }

        finish();
        return true;
    }

    private void begin() {
        ability.reset();
        began = true;
    }

    private void finish() {
        began = false;
    }
}
