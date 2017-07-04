package com.workasintended.chromaggus.ability;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.workasintended.chromaggus.Unit;

/**
 * Created by mazimeng on 1/23/16.
 */
public abstract class OffensiveAbilityAdaptor implements Ability {
    private float castingTime = 0;
    private Unit user;
    private Unit target;
    private float castRange = 32;
    private float castingProgress = 0;
    private float cooldown = 0;
    private float cooldownProgress = 0;



    public OffensiveAbilityAdaptor(Unit user, Unit target) {
        this.user = user;
        this.target = target;
    }

    public abstract Ability clone();

    @Override
    public void effect() {
        int exp = experience();
    }


    protected void useAbility() {
        effect();
    }

    @Override
    public void update(float delta) {
        if (cooldownProgress < cooldown) {
            cooldownProgress = Math.min(cooldownProgress + delta, cooldown);
        }
    }

    @Override
    public float getCastingTime() {
        return castingTime;
    }

    @Override
    public void use() {
        if(!inRange()) return;
        useAbility();
        reset();
        startCoolingDown();
    }


    @Override
    public boolean inRange() {
        float dst2 = Vector2.dst2(user.getX(Align.center), user.getY(Align.center),
                target.getX(Align.center), target.getY(Align.center));
        return dst2 <= castRange * castRange;
    }

    @Override
    public boolean cast(float delta) {
        if(isCoolingDown()) return false;

        castingProgress = Math.min(castingProgress + delta, castingTime);
        return castingProgress == castingTime;
    }

    @Override
    public void reset() {
        this.castingProgress = 0;
    }

    @Override
    public void setTarget(Unit target) {
        this.target = target;
    }

    @Override
    public void setUser(Unit unit) {
        this.user = unit;
    }

    public Unit getUser() {
        return user;
    }

    public Unit getTarget() {
        return target;
    }

    public float getCastRange() {
        return castRange;
    }

    public void setCastingTime(float castingTime) {
        this.castingTime = castingTime;
    }

    public void setCastRange(float castRange) {
        this.castRange = castRange;
    }


    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    protected int experience() {
        if (user.combat != null) {
            if (target.dead()) {
                return user.combat.gainExperienceFromKill();
            } else {
                return user.combat.gainExperienceFromAttack();
            }
        }
        return 0;
    }

    protected void startCoolingDown() {
        cooldownProgress = 0;
    }

    private boolean isCoolingDown() {
        return cooldownProgress < cooldown;
    }

    @Override
    public float getCooldownProgress() {
        return cooldownProgress;
    }

    public void setCooldownProgress(float cooldownProgress) {
        this.cooldownProgress = cooldownProgress;
    }

    public float getCastingProgress() {
        return castingProgress;
    }

    public void setCastingProgress(float castingProgress) {
        this.castingProgress = castingProgress;
    }

    @Override
    public boolean isCasting() {
        return castingProgress != castingTime;
    }

    @Override
    public boolean stop() {
        return target.dead();
    }

    public float getCooldown() {
        return cooldown;
    }
}
