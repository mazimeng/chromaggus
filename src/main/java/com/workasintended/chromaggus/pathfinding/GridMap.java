package com.workasintended.chromaggus.pathfinding;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.workasintended.chromaggus.Unit;

import java.util.LinkedList;

/**
 * Created by mazimeng on 6/27/15.
 */
public class GridMap {
    Grid[][] grids;
    int size;
    int ratio = 32;
    public GridMap(int size) {
        this.size = size;
        grids = new Grid[size][size];
        for(int i=0; i<size; ++i){
            for(int j=0; j<size; ++j) {
                Grid grid = new Grid();
                grid.x = i;
                grid.y = j;
                grids[i][j] = grid;
            }
        }
    }

    public Grid grid(int x, int y) {
        if(x<0 || x>=size || y<0 || y>=size) return null;
        return grids[x][y];
    }

    public Grid grid(float worldX, float worldY) {
        int x = (int) (worldX / ratio);
        int y = (int) (worldY / ratio);

        return grid(x, y);
    }

    public Vector2 align(float worldX, float worldY) {
        Grid grid = grid(worldX, worldY);
        return getWorldCoordinate(grid.x, grid.y);
    }

    public Vector2 align(Vector2 worldCoordate) {
        return align(worldCoordate.x, worldCoordate.y);
    }

    public Grid grid(Unit unit) {
        int x = (int) (unit.getX(Align.center) / ratio);
        int y = (int) (unit.getY(Align.center) / ratio);

        return grid(x, y);
    }

    public Vector2 getWorldCoordinate(int x, int y) {
        return new Vector2(x*ratio, y*ratio);
    }


//    public LinkedList<Grid> adjacents(Grid grid) {
//        return adjacents(grid, 1);
//    }
    public LinkedList<Grid> adjacents(Grid grid, int radius) {
        LinkedList<Grid> adjacents = new LinkedList<>();
        for(int i=-radius; i<radius+1; ++i) {
            for(int j=-radius; j<radius+1; ++j) {
                if(i==0 && j==0) continue;

                int x = grid.x+i;
                int y = grid.y+j;
                if((x < size && x >= 0) && (y < size && y >= 0)) {
                    adjacents.add(grids[x][y]);
                }
            }
        }

        return adjacents;
    }
    public LinkedList<Grid> walkableAdjacents(Grid grid) {
        LinkedList<Grid> adjacents = new LinkedList<>();
        boolean north = false,
                east = false,
                south = false,
                west = false;
        if(grid.x+1 < size && grid.x+1 >= 0 && grids[grid.x+1][grid.y].state==Grid.State.Walkable) {
            adjacents.add(grids[grid.x+1][grid.y]);
            north = true;
        }

        if(grid.x-1 < size && grid.x-1 >= 0 && grids[grid.x-1][grid.y].state==Grid.State.Walkable) {
            adjacents.add(grids[grid.x-1][grid.y]);
            south = true;
        }

        if(grid.y+1 < size && grid.y+1 >= 0 && grids[grid.x][grid.y+1].state==Grid.State.Walkable) {
            adjacents.add(grids[grid.x][grid.y+1]);
            east = true;
        }

        if(grid.y-1 < size && grid.y-1 >= 0 && grids[grid.x][grid.y-1].state==Grid.State.Walkable) {
            adjacents.add(grids[grid.x][grid.y-1]);
            west = true;
        }

        if(north && east && grids[grid.x+1][grid.y+1].state == Grid.State.Walkable) {
            adjacents.add(grids[grid.x+1][grid.y+1]);
        }

        if(east && south && grids[grid.x-1][grid.y+1].state == Grid.State.Walkable) {
            adjacents.add(grids[grid.x-1][grid.y+1]);
        }

        if(south && west && grids[grid.x-1][grid.y-1].state == Grid.State.Walkable) {
            adjacents.add(grids[grid.x-1][grid.y-1]);
        }

        if(west && north && grids[grid.x+1][grid.y-1].state == Grid.State.Walkable) {
            adjacents.add(grids[grid.x+1][grid.y-1]);
        }

        return adjacents;
    }


}
