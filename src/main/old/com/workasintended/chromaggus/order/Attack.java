package com.workasintended.chromaggus.order;//package com.workasintended.chromaggus.order;
//
//import com.workasintended.chromaggus.Unit;
//import com.workasintended.chromaggus.ability.Ability;
//import com.workasintended.chromaggus.ability.AbilityTarget;
//import com.workasintended.chromaggus.pathfinding.Grid;
//import com.workasintended.chromaggus.pathfinding.GridMap;
//
//import java.util.Observable;
//import java.util.Observer;
//
///**
// * Created by mazimeng on 8/1/15.
// */
//public class Attack extends Order {
//    private GridMap gridMap;
//    private Unit attacker;
//    private Unit target;
//    private Runnable onOutOfRange = new Runnable() {
//        @Override
//        public void run() {
//
//        }
//    };
//
//    public Attack(Unit attacker, Unit target) {
//        this.attacker = attacker;
//        this.target = target;
//        this.gridMap = attacker.getWorld().getGridMap();
//    }
//
//    @Override
//    public void update(float delta) {
//        if (!inRange(attacker, target)) {
//            Chase chase = new Chase(attacker, target);
//            chase.onComplete().addObserver(new Observer() {
//                @Override
//                public void update(Observable o, Object arg) {
//                    attacker.setOrder(new Attack(attacker, target));
//                }
//            });
//
//            attacker.setOrder(chase);
//        } else if (attacker != target) {
//            if (target.hp <= 0) {
//                attacker.setOrder(new Idle());
//                return;
//            }
//            attacker.abilities[Ability.MELEE].use(new AbilityTarget(target));
//        }
//    }
//
//    @Override
//    public void stop() {
//        attacker.abilities[Ability.MELEE].stop();
//        super.stop();
//    }
//
//    @Override
//    public void start() {
//        attacker.abilities[Ability.MELEE].reset();
//    }
//
//    private boolean inRange(Unit attacker, Unit target) {
//        Grid a = gridMap.grid(attacker);
//        Grid b = gridMap.grid(target);
//
//        return Math.abs(a.x - b.x) <= 1 && Math.abs(a.y - b.y) <= 1;
//    }
//}
