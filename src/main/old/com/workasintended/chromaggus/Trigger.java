package com.workasintended.chromaggus;

import java.util.Observable;

/**
 * Created by mazimeng on 8/30/15.
 */
public class Trigger extends Observable {
    public void trigger() {
        setChanged();
        super.notifyObservers();
    }
}
