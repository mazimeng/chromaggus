package com.workasintended.chromaggus.action;//package com.workasintended.chromaggus.action;
//
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.scenes.scene2d.Action;
//import com.badlogic.gdx.utils.Align;
//import com.workasintended.chromaggus.*;
//import com.workasintended.chromaggus.event.DebugRendererArgument;
//import com.workasintended.chromaggus.event.TakeDamageEvent;
//
///**
// * Created by mazimeng on 1/16/16.
// */
//public class Attack extends Action{
//    private Unit attacker;
//    private Unit targetUnit;
//
//    private float attackCooldown = 2;
//    private float nextAttack = 2;
//
//    public Attack(Unit attacker, Unit targetUnit) {
//        this.attacker = attacker;
//        this.targetUnit = targetUnit;
//    }
//
//    @Override
//    public boolean act(float delta) {
//        if((attacker.getFaction().isFriend(targetUnit.getFaction()))) return true;
//
//        if(targetUnit.dead()) return true;
//
//        if(closeEnough(delta)) {
//            nextAttack -= delta;
//
//            if(nextAttack<=0) {
//                targetUnit.combat.takeDamage(attacker.combat.getStrength());
//                nextAttack = attackCooldown;
//
//                experience(targetUnit.dead());
//            }
//
//            return false;
//        }
//        else {
//            nextAttack = attackCooldown;
//            return true;
//        }
//    }
//
//    private void experience(boolean killed) {
//        if(killed) attacker.combat.gainExperienceFromKill();
//        else this.attacker.combat.gainExperienceFromAttack();
//    }
//
//    private boolean closeEnough(float delta) {
//        Unit self = attacker;
//        Vector2 nextPosition = new Vector2(self.getX(Align.center), self.getY(Align.center));
//        float distance2 = Vector2.dst2(nextPosition.x, nextPosition.y, targetUnit.getX(Align.center), targetUnit.getY(Align.center));
//
//
//        return distance2 <= self.radius*self.radius+targetUnit.radius*targetUnit.radius;
//    }
//}
