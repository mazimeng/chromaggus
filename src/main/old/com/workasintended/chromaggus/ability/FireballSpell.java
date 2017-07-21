package com.workasintended.chromaggus.ability;//package com.workasintended.chromaggus.ability;
//
//import com.badlogic.gdx.graphics.Camera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.math.MathUtils;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.scenes.scene2d.Action;
//import com.badlogic.gdx.scenes.scene2d.actions.RemoveActorAction;
//import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
//import com.badlogic.gdx.utils.Align;
//import com.workasintended.chromaggus.ActorFactory;
//import com.workasintended.chromaggus.Effect;
//import com.workasintended.chromaggus.Service;
//import com.workasintended.chromaggus.Unit;
//import com.workasintended.chromaggus.action.EffectAction;
//import com.workasintended.chromaggus.action.MoveToUnit;
//
///**
// * Created by mazimeng on 1/22/16.
// */
//public class FireballSpell implements Ability {
//    private float castingTime = 0;
//    private Unit user;
//    private Unit target;
//    private float castRange;
//    private float progress = 0;
//
//    public FireballSpell() {
//        this(2f, null, null, 128f);
//    }
//
//    public FireballSpell(float castingTime, Unit user, Unit target, float castRange) {
//        this.castingTime = castingTime;
//        this.user = user;
//        this.target = target;
//        this.castRange = castRange;
//    }
//
//    @Override
//    public void update(float delta) {
//
//    }
//
//    @Override
//    public float getCastingTime() {
//        return castingTime;
//    }
//
//    @Override
//    public void use() {
//        if(target.dead()) return;
//
//        Effect fireball = ActorFactory.instance().fireball();
//        fireball.setPosition(user.getX(Align.center), user.getY(Align.center), Align.center);
//
//        MoveToUnit moveToUnit = new MoveToUnit(target, 256, 16);
//        EffectAction effectAction = new EffectAction(this);
//        RemoveActorAction removeActorAction = new RemoveActorAction();
//        removeActorAction.setActor(fireball);
//
//        SequenceAction sequenceAction = new SequenceAction(moveToUnit, effectAction, removeActorAction);
//        fireball.addAction(sequenceAction);
//
//        float angle = new Vector2(target.getX(Align.center), target.getY(Align.center))
//                .sub(user.getX(Align.center), user.getY(Align.center))
//                .angle();
//        fireball.setRotation(angle);
//
//        user.getWorld().addActor(fireball);
//        reset();
//    }
//
//    @Override
//    public boolean inRange() {
//        float dst2 = Vector2.dst2(user.getX(Align.center), user.getY(Align.center),
//                target.getX(Align.center), target.getY(Align.center));
//        return dst2 <= castRange*castRange;
//    }
//
//    @Override
//    public boolean cast(float delta) {
//        progress = Math.min(progress+delta, castingTime);
//        return progress==castingTime;
//    }
//
//    @Override
//    public void reset() {
//        this.progress = 0;
//    }
//
//    @Override
//    public void setTarget(Unit target) {
//        this.target = target;
//    }
//
//    @Override
//    public void setUser(Unit unit) {
//        this.user = unit;
//    }
//
//    public Unit getUser() {
//        return user;
//    }
//
//    public Unit getTarget() {
//        return target;
//    }
//
//    public float getCastRange() {
//        return castRange;
//    }
//
//    @Override
//    public void effect() {
//        if(target.combat !=null) {
//            target.combat.takeDamage(5);
//        }
//    }
//
//    @Override
//    public float getCooldownProgress() {
//        return 0;
//    }
//
//    @Override
//    public float getCastingProgress() {
//        return 0;
//    }
//
//    @Override
//    public void setCastingProgress(float castingProgress) {
//
//    }
//
//    @Override
//    public boolean isCasting() {
//        return false;
//    }
//
//    @Override
//    public String toString() {
//        return "Fireball";
//    }
//}
