package com.workasintended.chromaggus.order;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.workasintended.chromaggus.Trigger;
import com.workasintended.chromaggus.Unit;
import com.workasintended.chromaggus.pathfinding.Grid;
import com.workasintended.chromaggus.pathfinding.GridMap;
import com.workasintended.chromaggus.pathfinding.Pathfinder;

import java.util.LinkedList;

/**
 * Created by mazimeng on 8/1/15.
 */
public class Move extends Order {
    Pathfinder pathfinder;
    GridMap gridMap;
    Unit unit;
    LinkedList<Grid> path = new LinkedList<>();
    float speed = 50.0f;
    int ratio = 32;
    boolean complete = false;

    private Trigger onComplete = new Trigger();
    private Trigger onArrive = new Trigger();

    private Grid location;

    public Move(Unit unit, Grid location) {
        this.unit = unit;
        this.location = location;
        this.gridMap = unit.getWorld().getGridMap();
        this.pathfinder = new Pathfinder(gridMap);
    }

    public Move(Unit unit, Vector2 location) {
        this.unit = unit;
        this.gridMap = unit.getWorld().getGridMap();
        Grid grid = gridMap.grid(location.x, location.y);
        this.location = grid;
        this.pathfinder = new Pathfinder(gridMap);
    }

    public void setLocation(Vector2 location) {
        Grid grid = gridMap.grid(location.x, location.y);
        this.location = grid;
    }

    @Override
    public void start() {
        if(path.size()==0) {

            int x = (int) (unit.getX() / ratio);
            int y = (int) (unit.getY() / ratio);
            Grid start = gridMap.grid(x, y);

            LinkedList<Grid> newPath = pathfinder.find(start, location);
            if(newPath.size()==0) {
                complete = true;
                onComplete().trigger();
                return;
            }

            MoveTo move = new MoveTo(start);
            move.setTarget(unit);

            unit.getActions().add(move);



//            while(path.size()>1) {
//                path.removeFirst();
//            }

            while(newPath.size()>0) {
                path.addFirst(newPath.pollLast());
            }
        }
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Trigger onComplete() {
        return onComplete;
    }

    public Trigger onArrive() {
        return onArrive;
    }

    @Override
    public void stop() {
        this.setStop(true);

        //if current order is completed, trigger onStop immediately
        if(complete) getOnStop().run();
    }


    class MoveTo extends MoveToAction {
        private Grid startGrid;
        public MoveTo(Grid startGrid) {
            this.startGrid = startGrid;
        }

        @Override
        protected void begin() {
            super.begin();

            Vector2 currentLocation = new Vector2(startGrid.x * 32, startGrid.y * 32);

            Grid nextGrid = path.peekLast();

            Move.this.getUnit().occupy(nextGrid);
            Move.this.getUnit().release(startGrid);

            Vector2 next = new Vector2(nextGrid.x * 32, nextGrid.y * 32);
            float distance = next.dst(currentLocation);
            float duration = distance / speed;

            setPosition(next.x, next.y);
            setDuration(duration);
        }

        @Override
        protected void end() {
            super.end();

            if(getStop()) {
                if(getOnStop() != null) getOnStop().run();
                return;
            }



            Grid grid = path.pollLast();
//            if((path.size()==1 && path.peekLast().state != Grid.State.Walkable) || path.size()==0) {
            if(path.size()==0) {
                path.clear();

                complete = true;
                onComplete.trigger();

                System.out.format("move complete, %s%n", onComplete.countObservers());
            }
            else if (path.size()>0) {

                Grid nextGrid = path.peekLast();
                if(nextGrid.state == Grid.State.Blocked) {
                    LinkedList<Grid> newPath = pathfinder.find(grid, Move.this.location);
                    path.clear();
                    while(newPath.size()>0) {
                        path.addFirst(newPath.pollLast());
                    }
                }

                if(path.size()>0) {
                    MoveTo move = new MoveTo(grid);
                    move.setTarget(unit);
                    unit.getActions().add(move);
                }
            }

            if(onArrive!=null) {
                onArrive().trigger();
            }
        }
    }
}
