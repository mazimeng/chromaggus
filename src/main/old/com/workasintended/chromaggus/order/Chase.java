package com.workasintended.chromaggus.order;

import com.badlogic.gdx.math.Vector2;
import com.workasintended.chromaggus.Unit;
import com.workasintended.chromaggus.pathfinding.Grid;
import com.workasintended.chromaggus.pathfinding.GridMap;

/**
 * Created by mazimeng on 8/1/15.
 */
public class Chase extends Move {
    private Grid chasingLastGrid;
    private Unit target;
    private GridMap gridMap;

    public Chase(Unit chaser, Unit chasing) {
        super(chaser, new Vector2(chasing.getX(), chasing.getY()));
        this.target = chasing;
        this.gridMap = chaser.getWorld().getGridMap();
        this.chasingLastGrid = gridMap.grid(chasing);

    }

    @Override
    public void update(float delta) {
        Grid chasingCurrentGrid = gridMap.grid(getTarget());
        if(chasingCurrentGrid != chasingLastGrid) {
            System.out.format("catching up, %s%n", chasingCurrentGrid);
            setLocation(new Vector2(getTarget().getX(), getTarget().getY()));
            start();
        }
        chasingLastGrid = chasingCurrentGrid;
    }

    public Unit getTarget() {
        return target;
    }

    public void setTarget(Unit target) {
        this.target = target;
    }
}
