package com.workasintended.chromaggus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by mazimeng on 1/23/16.
 */
public class Effect extends Actor {
    private Animation<TextureRegion> animation;
    private float time = 0;
    private float rotationOffset = 0;

    public Effect(Animation animation) {
        this.animation = animation;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Actor unit = this;
        float delta = Gdx.graphics.getDeltaTime();
        TextureRegion frame = animation.getKeyFrame(time, true);

        batch.draw(frame
                , unit.getX(), unit.getY()
                , unit.getOriginX(), unit.getOriginY()
                , unit.getWidth(), unit.getHeight()
                , unit.getScaleX(), unit.getScaleY()
                , unit.getRotation() + getRotationOffset());


        super.draw(batch, parentAlpha);

        time+=delta;
    }

    public float getRotationOffset() {
        return rotationOffset;
    }

    public void setRotationOffset(float rotationOffset) {
        this.rotationOffset = rotationOffset;
    }
}
