package com.workasintended.chromaggus.event;

import com.workasintended.chromaggus.Event;
import com.workasintended.chromaggus.EventName;

/**
 * Created by mazimeng on 1/18/16.
 */
public class CameraZoomEvent extends Event {
    private int dir;

    public CameraZoomEvent(int dir) {
        super(EventName.CAMERA_ZOOM);
        this.dir = dir;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }
}
