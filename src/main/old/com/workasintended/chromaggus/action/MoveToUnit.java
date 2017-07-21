package com.workasintended.chromaggus.action;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.workasintended.chromaggus.DebugRenderer;
import com.workasintended.chromaggus.Event;
import com.workasintended.chromaggus.EventName;
import com.workasintended.chromaggus.Service;
import com.workasintended.chromaggus.event.DebugRendererArgument;

/**
 * Created by mazimeng on 1/16/16.
 */
public class MoveToUnit extends MoveToPosition{
    private Actor targetUnit;
    private float radius;

    public MoveToUnit(Actor targetUnit, float speed, float radius) {
        super(new Vector2(targetUnit.getX(Align.center), targetUnit.getY(Align.center)), speed);
        this.targetUnit = targetUnit;
        this.radius = radius;
    }

//    public MoveToUnit(Unit targetUnit) {
//        super(new Vector2());
//        this.targetUnit = targetUnit;
//    }

    @Override
    public boolean act(float delta) {
        this.begin();
        this.updateTargetPosition();

        if(closeEnough(delta)) return true;

        Actor unit = getActor();
        Vector2 position = new Vector2(unit.getX(getAlignment()), unit.getY(getAlignment()));
        Service.eventQueue().enqueue(new Event(EventName.SET_DEBUG_RENDERER,
                new DebugRendererArgument("direction_"+unit.hashCode(),
                        new DebugRenderer.LineRenderer(position.x, position.y, getTargetPosition().x, getTargetPosition().y))));

        move(delta);
        return false;
    }

    private boolean closeEnough(float delta) {
        Actor self = getActor();
        Vector2 nextPosition = new Vector2(self.getX(getAlignment()), self.getY(getAlignment()));
        float distance2 = Vector2.dst2(nextPosition.x, nextPosition.y, targetUnit.getX(getAlignment()), targetUnit.getY(getAlignment()));


        return distance2 <= radius*radius;
    }

    @Override
    protected boolean completed(float delta) {
        return targetUnit==null;
    }

    private void updateTargetPosition() {
        this.setTargetPosition(new Vector2(targetUnit.getX(getAlignment()), targetUnit.getY(getAlignment())));
    }


}
