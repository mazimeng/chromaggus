package com.workasintended.chromaggus.event;

import com.badlogic.gdx.math.Vector2;
import com.workasintended.chromaggus.CityArmory;
import com.workasintended.chromaggus.Event;
import com.workasintended.chromaggus.EventName;

/**
 * Created by mazimeng on 2/2/16.
 */
public class TransferItemEvent extends Event {
    private Vector2 targetPosition;
    private CityArmory.InventorySlot slot;

    public TransferItemEvent(CityArmory.InventorySlot slot, Vector2 targetPosition) {
        super(EventName.TRANSFER_ITEM);
        this.slot = slot;
        this.targetPosition = targetPosition;
    }

    public Vector2 getTargetPosition() {
        return targetPosition;
    }

    public CityArmory.InventorySlot getSlot() {
        return slot;
    }
}
