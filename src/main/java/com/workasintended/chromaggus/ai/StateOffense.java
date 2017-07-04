package com.workasintended.chromaggus.ai;//package com.workasintended.chromaggus.ai;
//
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.utils.Align;
//import com.workasintended.chromaggus.Service;
//import com.workasintended.chromaggus.Unit;
//import com.workasintended.chromaggus.WorldStage;
//import com.workasintended.chromaggus.event.AttackUnitEvent;
//
///**
// * Created by mazimeng on 1/18/16.
// */
//public class StateOffense extends AiComponent {
//    private Unit target;
//    private Vector2 initialPosition;
//    private boolean started = false;
//    private float safeRadius = 128;
//    public StateOffense(AiComponent previous, Unit target) {
//        this(previous.getSelf(), target, previous.getStage());
//    }
//
//    public StateOffense(Unit self, Unit target, WorldStage stage) {
//        super(self, stage);
//        this.target = target;
//        this.initialPosition = new Vector2(getSelf().getX(Align.center), getSelf().getY(Align.center));
//
//        Service.eventQueue().enqueue(new AttackUnitEvent(getSelf(), target));
//    }
//
//    @Override
//    public void update(float delta) {
//        super.update(delta);
//
//        if(target.dead() || !inSafeRadius()) {
//            if(!exit()) {
//                getSelf().ai = new StateReturn(this, initialPosition);
//            }
//        }
//    }
//
//    protected boolean inSafeRadius() {
//        return Vector2.dst2(initialPosition.x, initialPosition.y,
//                getSelf().getX(Align.center), getSelf().getY(Align.center)) <= safeRadius*safeRadius;
//    }
//
//}
