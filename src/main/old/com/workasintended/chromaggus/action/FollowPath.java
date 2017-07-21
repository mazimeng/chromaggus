package com.workasintended.chromaggus.action;

import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.workasintended.chromaggus.pathfinding.GridMap;

/**
 * Created by mazimeng on 6/28/15.
 */
public class FollowPath extends MoveToAction {
    GridMap gridMap;
    public FollowPath(float x, float y, float duration, GridMap gridMap) {
        this.gridMap = gridMap;
        this.setX(x);
        this.setY(y);
        this.setDuration(duration);
    }
    @Override
    protected void begin() {
//        Grid from = gridMap.grid((int)getActor().getX()/32, (int)getActor().getY()/32);
//        Grid to = gridMap.grid((int)getX()/32, (int)getY()/32);
//        from.state = Grid.State.Walkable;
//        to.state = Grid.State.Blocked;
    }

    @Override
    protected void end() {

    }


}
