package com.workasintended.chromaggus;

/**
 * Created by mazimeng on 2/5/16.
 */
public class EquipEvent extends com.badlogic.gdx.scenes.scene2d.Event {
    private CityArmory.Item item;

    public EquipEvent(CityArmory.Item item) {
        this.item = item;
    }

    public CityArmory.Item getItem() {
        return item;
    }
}
