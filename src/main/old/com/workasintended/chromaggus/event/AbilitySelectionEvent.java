package com.workasintended.chromaggus.event;

import com.workasintended.chromaggus.Event;
import com.workasintended.chromaggus.EventName;
import com.workasintended.chromaggus.ability.AbilityName;

/**
 * Created by mazimeng on 1/22/16.
 */
public class AbilitySelectionEvent extends Event {
    private AbilityName abilityName;
    private boolean selectedOrDeselected;

    public AbilitySelectionEvent(AbilityName abilityName, boolean selectedOrDeselected) {
        super(EventName.ABILITY_SELECTION);
        this.abilityName = abilityName;
        this.selectedOrDeselected = selectedOrDeselected;
    }

    public boolean isSelectedOrDeselected() {
        return selectedOrDeselected;
    }

    public void setSelectedOrDeselected(boolean selectedOrDeselected) {
        this.selectedOrDeselected = selectedOrDeselected;
    }

    public AbilityName getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(AbilityName abilityName) {
        this.abilityName = abilityName;
    }
}
