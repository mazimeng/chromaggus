package com.workasintended.chromaggus;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.workasintended.chromaggus.ability.Ability;

/**
 * Created by mazimeng on 1/30/16.
 */
public class Weapon extends CityArmory.Item{
    private Ability ability;

    public Weapon(Drawable drawable) {
        super(drawable);
    }
    public Weapon(Drawable drawable, Ability ability) {
        super(drawable);
        this.ability = ability;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public void update(float delta) {
        if(ability!=null) {
            ability.update(delta);
        }
    }

    @Override
    public CityArmory.Item clone() {
        Ability weaponAbility = null;
        if(this.ability!=null) {
            weaponAbility = this.ability.clone();
        }
        Weapon weapon = new Weapon(this.getDrawable(), weaponAbility);
        return weapon;
    }
}
