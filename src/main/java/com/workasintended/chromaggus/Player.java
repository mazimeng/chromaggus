package com.workasintended.chromaggus;

import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.workasintended.chromaggus.ability.Ability;
import com.workasintended.chromaggus.action.MoveToUnit;
import com.workasintended.chromaggus.action.UseAbility;
import com.workasintended.chromaggus.event.GainGoldEvent;
import com.workasintended.chromaggus.event.UnitSelectionEvent;
import com.workasintended.chromaggus.event.UseAbilityEvent;

/**
 * Created by mazimeng on 1/21/16.
 */
public class Player implements EventHandler {
    private Faction faction;
    private Unit selected = null;
    private float gold = 0;

    public Player() {
    }

    @Override
    public void handle(Event event) {
        if(event.is(EventName.UNIT_SELECTED)) {
            UnitSelectionEvent unitSelectedEvent = event.cast(UnitSelectionEvent.class);
            Unit newSelection = unitSelectedEvent.getUnit();

            if(selected!=null) {
                if(selected.renderer!=null) selected.renderer.setSelected(false);
            }
            if(newSelection.renderer!=null) {
                newSelection.renderer.setSelected(true);
            }

            selected = unitSelectedEvent.getUnit();

            if(selected.combat!=null) System.out.println(selected.combat);
        }
        if(event.is(EventName.UNIT_DESELECTED)) {
            if(selected!=null && selected.renderer!=null) selected.renderer.setSelected(false);
            selected = null;

        }
        if(event.is(EventName.GAIN_GOLD)) {
            GainGoldEvent gainGoldEvent = event.cast(GainGoldEvent.class);
            this.gold += gainGoldEvent.getGold();
        }

        handleBuyItem(event);
        handleUseAbility(event);
    }

    private void handleUseAbility(Event event) {
        if(!event.is(EventName.USE_ABILITY)) return;
        if(selected==null) return;


        UseAbilityEvent useAbilityEvent = event.cast(UseAbilityEvent.class);
        Unit target = useAbilityEvent.getTarget();
        Ability ability = useAbilityEvent.getAbility();
        ability.setUser(selected);
        ability.setTarget(useAbilityEvent.getTarget());

        UseAbility useAbility = new UseAbility(ability);

        MoveToUnit moveToUnit = new MoveToUnit(target, selected.getSpeed(), ability.getCastRange());
        SequenceAction sequenceAction = new SequenceAction(moveToUnit, useAbility);

        RepeatAction repeat = new RepeatAction();
        repeat.setAction(sequenceAction);
        repeat.setCount(RepeatAction.FOREVER);

        selected.clearActions();
        selected.addAction(repeat);

        System.out.println("casting " + ability.toString());

    }

    private void handleBuyItem(Event event) {
        if(!event.is(EventName.BUY_ITEM)) return;
        if(selected==null) return;
        if(selected.combat == null) return;

        float cost = 1000;
        if(gold<cost) return;

        gold -= cost;
        selected.combat.gainExperience(300);
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }
}
