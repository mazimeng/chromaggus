package com.workasintended.chromaggus;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by mazimeng on 1/16/16.
 */
public abstract class DebugRenderer {
    public abstract void render(ShapeRenderer renderer);

    public static class LineRenderer extends DebugRenderer {
        private final float x1;
        private final float y1;
        private final float x2;
        private final float y2;

        public LineRenderer(float x1, float y1, float x2, float y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
        @Override
        public void render(ShapeRenderer renderer) {
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.line(x1, y1, x2, y2);
            renderer.end();

        }
    }
}
