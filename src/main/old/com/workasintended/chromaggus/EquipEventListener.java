package com.workasintended.chromaggus;

import com.badlogic.gdx.scenes.scene2d.*;

/**
 * Created by mazimeng on 2/5/16.
 */
public class EquipEventListener implements EventListener {
    private Unit user;

    public EquipEventListener(Unit user) {
        this.user = user;
    }

    @Override
    public boolean handle(com.badlogic.gdx.scenes.scene2d.Event event) {
        if(!(event instanceof EquipEvent)) return false;
        EquipEvent equipEvent = ((EquipEvent) event);

        if(user.combat==null) return true;
        if(equipEvent.getItem()==null || !(equipEvent.getItem() instanceof Weapon)) return true;
        Weapon weapon = (Weapon)equipEvent.getItem();
        if(weapon != user.combat.getPrimaryWeapon()) {
            user.combat.setPrimaryWeapon(weapon);
            System.out.println(String.format("equip weapon: %s, %s, %s, %s",
                    ((EquipEvent) event).getItem(),
                    event.getListenerActor(),
                    event.getTarget(),
                    event.getStage()));
        }

        return true;
    }
}
