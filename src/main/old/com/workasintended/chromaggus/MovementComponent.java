package com.workasintended.chromaggus;//package com.workasintended.chromaggus;
//
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
//import com.workasintended.chromaggus.pathfinding.Grid;
//import com.workasintended.chromaggus.pathfinding.GridMap;
//import com.workasintended.chromaggus.pathfinding.Pathfinder;
//
//import java.util.LinkedList;
//
///**
// * Created by mazimeng on 7/18/15.
// */
//public class MovementComponent {
//    Pathfinder pathfinder;
//    GridMap gridMap;
//    Unit unit;
//    LinkedList<Grid> path = new LinkedList<>();
//    float speed = 50.0f;
//    Grid end;
//    int ratio = 32;
//
//    private Runnable done;
//    private Runnable arrive;
//
//    public MovementComponent(GridMap gridMap) {
//        this.gridMap = gridMap;
//        this.pathfinder = new Pathfinder(gridMap);
//    }
//
//    public void move(Grid end) {
//        this.move(end, new Runnable() {
//            @Override
//            public void run() {
//            }
//        });
//    }
//
//    public void move(Grid end, Runnable done) {
//        this.end = end;
//        this.done = done;
//        if(path.size()==0) {
//
//            int x = (int) (unit.getX() / ratio);
//            int y = (int) (unit.getY() / ratio);
//            Grid start = gridMap.grid(x, y);
//
//            path.addLast(start);
//
//            Move move = new Move(start);
//            move.setTarget(unit);
//
//
//            unit.getActions().add(move);
//        }
//
//        LinkedList<Grid> newPath = pathfinder.find(path.peekLast(), end);
//
//        while(path.size()>1) {
//            path.removeFirst();
//        }
//
//        while(newPath.size()>0) {
//            path.addFirst(newPath.pollLast());
//        }
//    }
//
//    public Unit getUnit() {
//        return unit;
//    }
//
//    public void setUnit(Unit unit) {
//        this.unit = unit;
//    }
//
//    public Runnable getArrive() {
//        return arrive;
//    }
//
//    public void setArrive(Runnable arrive) {
//        this.arrive = arrive;
//    }
//
//    class Move extends MoveToAction {
//        private Grid startGrid;
//        public Move(Grid startGrid) {
//            this.startGrid = startGrid;
//        }
//
//        @Override
//        protected void begin() {
//            super.begin();
//
//            Vector2 currentLocation = new Vector2(startGrid.x * 32, startGrid.y * 32);
//
//            Grid nextGrid = path.peekLast();
//
//            MovementComponent.this.getUnit().occupy(nextGrid);
//            MovementComponent.this.getUnit().release(startGrid);
////            nextGrid.state = Grid.State.Blocked;
////            startGrid.state = Grid.State.Walkable;
//
//            Vector2 next = new Vector2(nextGrid.x * 32, nextGrid.y * 32);
//            float distance = next.dst(currentLocation);
//            float duration = distance / speed;
//
//            setPosition(next.x, next.y);
//            setDuration(duration);
//        }
//
//        @Override
//        protected void end() {
//            super.end();
//
//            Grid grid = path.pollLast();
//            if(path.size()==1 && path.peekLast().state != Grid.State.Walkable || path.size()==0) {
//                path.clear();
//                done.run();
//            }
//            else if (path.size()>0) {
//                Grid nextGrid = path.peekLast();
//                if(nextGrid.state == Grid.State.Blocked) {
//                    LinkedList<Grid> newPath = pathfinder.find(grid, end);
//                    path.clear();
//                    while(newPath.size()>0) {
//                        path.addFirst(newPath.pollLast());
//                    }
//                }
//
//                if(path.size()>0) {
//                    Move move = new Move(grid);
//                    move.setTarget(unit);
//                    unit.getActions().add(move);
//                }
//            }
//        }
//    }
//}
