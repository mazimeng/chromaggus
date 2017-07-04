package com.workasintended.chromaggus.event;

import com.workasintended.chromaggus.Event;
import com.workasintended.chromaggus.EventName;

/**
 * Created by mazimeng on 1/21/16.
 */
public class BuyItemEvent extends Event{
    public BuyItemEvent() {
        super(EventName.BUY_ITEM);
    }
}
