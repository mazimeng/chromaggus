package com.workasintended.chromaggus.event;

import com.workasintended.chromaggus.DebugRenderer;

/**
 * Created by mazimeng on 1/16/16.
 */
public class DebugRendererArgument {
    private String name;
    private DebugRenderer debugRenderer;

    public DebugRendererArgument(String name, DebugRenderer debugRenderer) {
        this.name = name;
        this.debugRenderer = debugRenderer;
    }

    public DebugRenderer getDebugRenderer() {
        return debugRenderer;
    }

    public void setDebugRenderer(DebugRenderer debugRenderer) {
        this.debugRenderer = debugRenderer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
