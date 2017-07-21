package com.workasintended.chromaggus.unitcomponent;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 7/11/15.
 */
public abstract class RendererComponent extends UnitComponent {
    public RendererComponent(Unit self) {
        super(self);
    }

    public void draw(Batch batch, float parentAlpha) {
    }

    public abstract TextureRegion getIcon();
}
