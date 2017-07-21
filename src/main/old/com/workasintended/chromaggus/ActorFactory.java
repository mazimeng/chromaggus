package com.workasintended.chromaggus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by mazimeng on 1/23/16.
 */
public class ActorFactory {
    private static ActorFactory unitFactory = new ActorFactory();
    private TextureRegion[][] icon06;
    private TextureRegion[][] icons;
    private TextureRegion[][] selection;

    public static ActorFactory instance() {
        return unitFactory;
    }

    public Effect fireball() {
        TextureRegion[] frames = new TextureRegion[]{ icon06()[20][2] };
        Animation animation = new Animation(0.5f, frames);
        Effect effect = new Effect(animation);
        effect.setSize(32, 32);
        effect.setOrigin(16, 16);
        effect.setRotationOffset(90f);
        return effect;
    }

    public Animation dead() {
        TextureRegion[] frames = new TextureRegion[]{ icon()[0][1] };
        Animation animation = new Animation(0.5f, frames);

        return animation;
    }

    public Animation selection() {
        if(selection==null) {
            Texture itemTexture = Service.assetManager().get("spritesheet/selection.png");
            selection = TextureRegion.split(itemTexture,
                    itemTexture.getWidth() / 2, itemTexture.getHeight() / 1);
        }
        TextureRegion[] frames = new TextureRegion[]{ selection[0][0], selection[0][1] };
        Animation animation = new Animation(0.5f, frames);
        return animation;
    }

    public TextureRegion[][] icon06() {
        if(icon06 == null) {
            Texture itemTexture = Service.assetManager().get("icon.06.png");
            icon06 = TextureRegion.split(itemTexture,
                    itemTexture.getWidth() / 14, itemTexture.getHeight() / 30);
        }

        return icon06;
    }
    public TextureRegion[][] icon() {
        if(icons == null) {
            Texture itemTexture = Service.assetManager().get("icon.png");
            icons = TextureRegion.split(itemTexture,
                    itemTexture.getWidth() / 16, itemTexture.getHeight() / 39);
        }

        return icons;
    }
}
