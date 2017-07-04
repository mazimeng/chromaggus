package com.workasintended.chromaggus.pathfinding;

import com.workasintended.chromaggus.Unit;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mazimeng on 6/27/15.
 */
public class Grid {
    public int x;
    public int y;
    public State state;
    private LinkedList<Unit> units = new LinkedList<>();

    public enum State {
        Walkable,
        Blocked,
        Occupied,
    }

    public Grid(){
        state = State.Walkable;
    }

    public Grid(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public float cost() {
        return state == State.Walkable?10:37;
    }

    @Override
    public String toString() {
        return "Grid{" +
                "x=" + x +
                ", y=" + y +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grid grid = (Grid) o;

        if (x != grid.x) return false;
        return y == grid.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
    }

    public List<Unit> getUnits() {
        return units;
    }
}
