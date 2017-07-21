package com.workasintended.chromaggus;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.workasintended.chromaggus.event.UnitSelectionEvent;

/**
 * Created by mazimeng on 1/24/16.
 */
public class UnitSelection extends Image implements EventHandler{
    private Drawable empty;
    public UnitSelection(Drawable empty) {
        super(empty);
        this.empty = empty;
    }

    @Override
    public void handle(Event event) {
        UnitSelectionEvent unitSelectedEvent = event.cast();
        if(event.is(EventName.UNIT_SELECTED)) {
            Unit unit = unitSelectedEvent.getUnit();
            if(unit.renderer==null) return;

            TextureRegion icon = unit.renderer.getIcon();
            this.setDrawable(new TextureRegionDrawable(icon));
        }
        if(event.is(EventName.UNIT_DESELECTED)) {
            this.setDrawable(null);
        }
    }
}