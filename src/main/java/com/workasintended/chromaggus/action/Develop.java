package com.workasintended.chromaggus.action;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Align;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 1/17/16.
 */
public class Develop extends Action {
    private Unit city;
    private float range = 64f;
    private float speed = 2f;

    public Develop(Unit city) {
        this.city = city;
    }

    @Override
    public boolean act(float delta) {
        if(this.city.city == null) return true;
        Unit self = (Unit)this.getActor();

        if(!self.getFaction().isFriend(city.getFaction())) return true;

        float dst2 = Vector2.dst2(self.getX(Align.center), self.getY(Align.center),
                city.getX(Align.center), city.getY(Align.center));

        if(dst2 <= range*range) {
            float development = city.city.getDevelopment();
            development = development + speed * delta;
            city.city.setDevelopment(development);
        }

        return false;
    }
}
