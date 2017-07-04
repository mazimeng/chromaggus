package com.workasintended.chromaggus.ability;

import com.badlogic.gdx.audio.Sound;
import com.workasintended.chromaggus.Service;

/**
 * Created by mazimeng on 1/22/16.
 */
public class Melee extends OffensiveAbilityAdaptor {
    private float power = 1.0f;
    public Melee() {
        super(null, null);
        setCastingTime(0.5f);
        setCastRange(32f);
        setCooldown(1f);
        setCooldownProgress(1f);
    }

    protected Melee(Melee melee) {
        super(null, null);
        this.setPower(melee.getPower());
        this.setCastingTime(melee.getCastingTime());
        this.setCastRange(melee.getCastRange());
        this.setCooldown(melee.getCooldown());
        this.setCooldownProgress(melee.getCooldownProgress());
    }

    @Override
    public Ability clone() {
        Melee clone = new Melee(this);

        return clone;
    }

    @Override
    public void effect() {
        getTarget().combat.takeDamage((int)(getUser().combat.getStrength() * power));
        super.effect();
    }


    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    @Override
    protected void useAbility() {
        super.useAbility();
        soundOnUse();
    }

    @Override
    public String toString() {
        return "Melee";
    }

    private void soundOnUse() {
        Sound sound = Service.assetManager().get("sound/melee.wav");
        sound.play();
    }
}
