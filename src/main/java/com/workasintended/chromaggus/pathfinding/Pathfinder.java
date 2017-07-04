package com.workasintended.chromaggus.pathfinding;

import java.util.*;

/**
 * Created by mazimeng on 6/27/15.
 */
public class Pathfinder {
    protected GridMap gridMap;
    protected int gridMapSize;
    public Pathfinder(GridMap gridMap) {
        this.gridMap = gridMap;
        this.gridMapSize = gridMap.size;
    }

    public LinkedList<Grid> find(Grid start, Grid end) {
        return this.find(start, end, gridMap);
    }

    public LinkedList<Grid> find(Grid start, Grid end, GridMap map) {
//        PriorityQueue<GridRecord> openList = new PriorityQueue<GridRecord>((a,b)->Float.compare(a.f, b.f));
        PriorityQueue<GridRecord> openList = new PriorityQueue<GridRecord>(1024, new Comparator<GridRecord>() {
            @Override
            public int compare(GridRecord o1, GridRecord o2) {
                return Float.compare(o1.f, o2.f);
            }
        });

        HashSet<Grid> closeSet = new HashSet<>();

        GridRecord startRecord = new GridRecord(start);
        startRecord.f = startRecord.g + h(start, end);
        openList.add(new GridRecord(start));

        while(!openList.isEmpty()) {
            GridRecord current = openList.poll();

            if(current.grid == end) {
                return constructPath(current);
            }

            closeSet.add(current.grid);
            LinkedList<Grid> adjacents = map.walkableAdjacents(current.grid);

            while(!adjacents.isEmpty()) {
                Grid neighbor = adjacents.pop();
                GridRecord neighborRecord = null;

                if(neighbor.state == Grid.State.Blocked && neighbor!=end) {
                    closeSet.add(neighbor);
                    continue;
                }
                if(closeSet.contains(neighbor)) continue;

                for(GridRecord record: openList) {
                    if (record.grid == neighbor) {
                        neighborRecord = record;
                        break;
                    }
                }

                float tentativeG = current.g + current.cost(neighbor);

                if(neighborRecord==null || tentativeG < neighborRecord.g) {
                    if(neighborRecord==null) {
                        neighborRecord = new GridRecord(neighbor);
                    }

                    neighborRecord.cameFrom = current;
                    neighborRecord.g = tentativeG;
                    neighborRecord.f = tentativeG + h(neighbor, end);
                    openList.add(neighborRecord);
                }
            }
        }

        return new LinkedList<>();
    }

    public LinkedList<Grid> find(float x, float y, Grid end, GridMap map) {
        PriorityQueue<GridRecord> openList = new PriorityQueue<GridRecord>(1024, new Comparator<GridRecord>() {
            @Override
            public int compare(GridRecord o1, GridRecord o2) {
                return Float.compare(o1.f, o2.f);
            }
        });
        HashSet<Grid> closeSet = new HashSet<>();

        Set<Grid> startSet = new HashSet<>();
        Grid g = null;
        g = map.grid((int)Math.ceil(x) , (int)Math.ceil(y));
        if(g!=null) {
            startSet.add(g);
        }
        g = map.grid((int)Math.floor(x) , (int)Math.floor(y));
        if(g!=null) {
            startSet.add(g);
        }
        g = map.grid((int)Math.ceil(x) , (int)Math.floor(y));
        if(g!=null) {
            startSet.add(g);
        }
        g = map.grid((int)Math.floor(x) , (int)Math.ceil(y));
        if(g!=null) {
            startSet.add(g);
        }

        for (Grid grid : startSet) {
            if(grid.state== Grid.State.Blocked) continue;
            GridRecord startRecord = new GridRecord(grid);
            startRecord.f = startRecord.g + h(grid.x, grid.y, end.x, end.y);
            openList.add(startRecord);
        }

        while(!openList.isEmpty()) {
            GridRecord current = openList.poll();

            if(current.grid == end) {
                return constructPath(current);
            }

            closeSet.add(current.grid);
            LinkedList<Grid> adjacents = map.walkableAdjacents(current.grid);

            while(!adjacents.isEmpty()) {
                Grid neighbor = adjacents.pop();
                GridRecord neighborRecord = null;

                if(neighbor.state == Grid.State.Blocked && neighbor!=end) {
                    closeSet.add(neighbor);
                    continue;
                }
                if(closeSet.contains(neighbor)) continue;

                for(GridRecord record: openList) {
                    if (record.grid == neighbor) {
                        neighborRecord = record;
                        break;
                    }
                }

                float tentativeG = current.g + current.cost(neighbor);

                if(neighborRecord==null || tentativeG < neighborRecord.g) {
                    if(neighborRecord==null) {
                        neighborRecord = new GridRecord(neighbor);
                    }

                    neighborRecord.cameFrom = current;
                    neighborRecord.g = tentativeG;
                    neighborRecord.f = tentativeG + h(neighbor, end);
                    openList.add(neighborRecord);
                }
            }
        }

        return new LinkedList<>();
    }

    public float h(Grid start, Grid end) {
        float v = 1.0f;
        return (Math.abs(start.x-end.x) + Math.abs(start.y - end.y))*v;
    }

    public float h(float x1, float y1, float x2, float y2) {
        float v = 1.0f;
        return (Math.abs(x1-x2) + Math.abs(y1 - y2))*v;
    }

    private LinkedList<Grid> constructPath(GridRecord current) {
        LinkedList<Grid> path = new LinkedList<>();
        while(current!=null) {
            if(current.grid.state == Grid.State.Blocked) {
                current = current.cameFrom;
                continue;
            }
            path.add(current.grid);

            current = current.cameFrom;
        }

        return path;
    }

    private class GridRecord {
        public Grid grid;
        public float h;
        public float g;
        public float f;
        public GridRecord cameFrom;

        public GridRecord(Grid grid) {
            this.grid = grid;
        }
        float cost(Grid grid) {
            float cost = this.grid.x!=grid.x && this.grid.y!=grid.y?
                    14:
                    10;
            return cost;
        }

        @Override
        public String toString() {
            return "GridRecord{" +
                    "grid=" + grid +
                    ", h=" + h +
                    ", g=" + g +
                    ", f=" + f +
                    '}';
        }
    }

    public static void main(String[] args) {
        GridMap gridMap = new GridMap(4);
        Pathfinder pathfinder = new Pathfinder(gridMap);

        gridMap.grid(1, 2).state = Grid.State.Blocked;
        gridMap.grid(2, 1).state = Grid.State.Blocked;
        gridMap.grid(2, 2).state = Grid.State.Blocked;

        LinkedList<Grid> path = pathfinder.find(2.0f, 0f, gridMap.grid(1, 3), gridMap);
        for (Grid grid : path) {
            System.out.println(grid.toString());
        }
    }
}
