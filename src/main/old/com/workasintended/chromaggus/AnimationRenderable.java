package com.workasintended.chromaggus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by mazimeng on 8/2/15.
 */
public class AnimationRenderable {
    private Animation<TextureRegion> animation;
    private float stateTime;
    private float x, y;
    private float speed = 8f;
    private float rotation = 0;

    public AnimationRenderable(Animation animation) {
        this.animation = animation;
    }

    public boolean render(Batch batch) {

        TextureRegion frame = animation.getKeyFrame(stateTime);
        batch.draw(frame, x, y, frame.getRegionWidth()*0.5f, frame.getRegionHeight()*0.5f,
                frame.getRegionWidth(), frame.getRegionHeight(), 1, 1,
                rotation);

        boolean complete = animation.isAnimationFinished(stateTime);

        stateTime += Gdx.graphics.getDeltaTime()*speed;
        return complete;

    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void resetAnimation() {
        this.stateTime = 0;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
