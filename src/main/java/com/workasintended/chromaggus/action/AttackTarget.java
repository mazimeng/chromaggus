package com.workasintended.chromaggus.action;//package com.workasintended.chromaggus.action;
//
//import com.badlogic.gdx.math.Vector2;
//import com.workasintended.chromaggus.*;
//import com.workasintended.chromaggus.event.DebugRendererArgument;
//
///**
// * Created by mazimeng on 1/16/16.
// */
//public class AttackTarget extends MoveToPosition {
//    private Unit targetUnit;
//
//    private float attackCooldown = 2;
//    private float nextAttack = 2;
//
//    public AttackTarget(Unit targetUnit) {
//        super(new Vector2());
//        this.targetUnit = targetUnit;
//    }
//
//    @Override
//    public boolean act(float delta) {
//        this.updateTargetPosition();
//
//        Unit unit = getUnit();
//        Vector2 position = new Vector2(unit.getX(getAlignment()), unit.getY(getAlignment()));
//        Service.eventQueue().enqueue(new Event(EventName.SET_DEBUG_RENDERER,
//                new DebugRendererArgument("AttackTarget",
//                        new DebugRenderer.LineRenderer(position.x, position.y, getTargetPosition().x, getTargetPosition().y))));
//
//        return super.act(delta);
//    }
//
//    @Override
//    protected void move(float delta) {
//        if(closeEnough(delta)) {
//            nextAttack -= delta;
//
//            if(nextAttack<=0) {
//                targetUnit.hp -= getUnit().strength;
//                nextAttack = attackCooldown;
//            }
//            return;
//        }
//        else {
//            nextAttack = attackCooldown;
//        }
//
//        super.move(delta);
//    }
//
//    private boolean closeEnough(float delta) {
//        Unit self = getUnit();
//        Vector2 nextPosition = this.nextPosition(delta);
//        float distance2 = Vector2.dst2(nextPosition.x, nextPosition.y, targetUnit.getX(getAlignment()), targetUnit.getY(getAlignment()));
//
//
//        return distance2 <= self.radius*self.radius+targetUnit.radius*targetUnit.radius;
//    }
//
//    @Override
//    protected boolean completed(float delta) {
//        return targetUnit==null;
//    }
//
//    private void updateTargetPosition() {
//        Unit unit = getUnit();
//        Vector2 position = new Vector2(unit.getX(getAlignment()), unit.getY(getAlignment()));
//
//        this.setTargetPosition(new Vector2(targetUnit.getX(getAlignment()), targetUnit.getY(getAlignment())));
//        this.setDirection(new Vector2(getTargetPosition()).sub(position).nor());
//    }
//}
