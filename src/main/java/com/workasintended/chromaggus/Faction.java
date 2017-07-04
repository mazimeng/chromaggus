package com.workasintended.chromaggus;

/**
 * Created by mazimeng on 1/21/16.
 */
public class Faction {
    private int value;

    public final static Faction FACTION_A = new Faction(1);
    public final static Faction FACTION_B = new Faction(2);

    public boolean isFriend(Faction faction) {
        return (this.value & faction.value) > 0;
    }
    public int getValue() {
        return value;
    }

    private Faction(int v) {
        this.value = v;
    }
}
