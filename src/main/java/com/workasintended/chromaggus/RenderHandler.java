package com.workasintended.chromaggus;

import com.workasintended.chromaggus.event.TakeDamageEvent;

/**
 * Created by mazimeng on 1/23/16.
 */
public class RenderHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        if(event.is(EventName.TAKE_DAMAGE)) {
            TakeDamageEvent takeDamageEvent = event.cast(TakeDamageEvent.class);
            Unit unit = takeDamageEvent.getUnit();
            if(unit.renderer!=null) unit.renderer.blink();
        }
    }
}
