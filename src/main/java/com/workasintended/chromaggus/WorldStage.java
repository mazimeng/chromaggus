package com.workasintended.chromaggus;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.workasintended.chromaggus.action.ShakeCamera;
import com.workasintended.chromaggus.event.*;
import com.workasintended.chromaggus.event.order.MoveToPositionEvent;
import com.workasintended.chromaggus.pathfinding.GridMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class WorldStage extends Stage implements EventHandler {
    public WorldStage() {
        super();
    }

    public WorldStage(Viewport viewport) {
        super(viewport);
    }

    private GridMap gridMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private ShapeRenderer shapeRenderer;

    private Rectangle selectionBox;
    private Vector2 cameraMovement = new Vector2();
    private float cameraZoomSpeed = 0.01f;
    private float cameraZoom = 0f;

    private HashMap<String, DebugRenderer> debugRenderers = new HashMap<>();

    public LinkedList<AnimationRenderable> animationRenderables = new LinkedList<>();

    @Override
    public void act(float delta) {
        // TODO Auto-generated method stub
        super.act(delta);

        int alive = 0;
        for (Actor actor : this.getActors()) {
            if (!(actor instanceof Unit)) continue;
            Unit unit = (Unit) actor;
            if (!unit.dead()) alive++;

        }

        this.updateCameraMovement(delta);
    }

    @Override
    public void draw() {
        OrthographicCamera camera = (OrthographicCamera) getCamera();
        getTiledMapRenderer().setView(camera);
        getTiledMapRenderer().render();

        super.draw();

        getBatch().begin();
        for (Iterator<AnimationRenderable> it = animationRenderables.iterator(); it.hasNext();) {
            AnimationRenderable animationRenderable = it.next();
            if (animationRenderable.render(getBatch())) {
                it.remove();
            }
        }
        getBatch().end();

        drawSelection();
        debug(shapeRenderer);
    }

    public void drawSelection() {
        if (selectionBox == null) return;
        shapeRenderer.setProjectionMatrix(getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(selectionBox.getX(), selectionBox.getY(), selectionBox.getWidth(), selectionBox.getHeight());

        shapeRenderer.end();
    }

    public Iterable<Unit> getUnits(float x, float y, float radius) {
        LinkedList<Unit> units = new LinkedList<Unit>();
        for (Actor actor : this.getActors()) {
            Unit unit = (actor instanceof Unit) ? (Unit) actor : null;
            if (unit == null) continue;

            float r2 = radius * radius;
            float d2 = Vector2.dst2(x, y, unit.getX(), unit.getY());
            if (r2 <= d2) {
                units.add(unit);
            }
        }

        return units;
    }

    public Iterable<Unit> getUnits() {
        LinkedList<Unit> units = new LinkedList<Unit>();
        for (Actor actor : this.getActors()) {
            Unit unit = (actor instanceof Unit) ? (Unit) actor : null;
            if (unit == null) continue;

            units.add(unit);
        }

        return units;
    }

    public GridMap getGridMap() {
        return gridMap;
    }

    public void setGridMap(GridMap gridMap) {
        this.gridMap = gridMap;
    }

    public OrthogonalTiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    public void setTiledMapRenderer(OrthogonalTiledMapRenderer tiledMapRenderer) {
        this.tiledMapRenderer = tiledMapRenderer;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    @Override
    public void handle(Event event) {
        if (event.getName() == EventName.SELECTION_STARTED) {
            selectionBox = event.getArgument(Rectangle.class);
            return;
        }
        if (event.getName() == EventName.SELECTING) {
            selectionBox = event.getArgument(Rectangle.class);
            return;

        }
        if (event.getName() == EventName.SELECTION_COMPLETED) {
            selectionBox = null;
            return;

        }

        if (event.getName() == EventName.MOVING_CAMERA) {
            Vector2 dir = event.getArgument(Vector2.class);
            //getCamera().translate(position.x, position.y, 0);
            dir.x = MathUtils.clamp(dir.x, -1, 1);
            dir.y = MathUtils.clamp(dir.y, -1, 1);
            cameraMovement.x = (cameraMovement.x + dir.x);
            cameraMovement.y = (cameraMovement.y + dir.y);

            return;
        }

        if(event.getName()== EventName.CAMERA_ZOOM) {
            CameraZoomEvent cameraZoomEvent = event.cast(CameraZoomEvent.class);
            int dir = cameraZoomEvent.getDir();
        }

        if(event.getName() == EventName.RENDER_ANIMATION) {
            AnimationRenderable animationRenderable = event.getArgument(AnimationRenderable.class);
            animationRenderables.add(animationRenderable);
            return;

        }

        if(event.getName()== EventName.UNIT_DIED) {
            Unit unit = event.getArgument(Unit.class);
            return;

        }
        if(event.getName()== EventName.SET_DEBUG_RENDERER) {
            DebugRendererArgument renderer = event.getArgument(DebugRendererArgument.class);
            this.debugRenderers.put(renderer.getName(), renderer.getDebugRenderer());
            return;
        }

        if(event.is(EventName.MOVE_TO_POSITION)) {
            MoveToPositionEvent moveToPositionEvent = event.cast(MoveToPositionEvent.class);
            Unit unit = moveToPositionEvent.getUnit();

            if(unit.movement!=null) unit.movement.moveToPosition(moveToPositionEvent.getPosition());
        }

        if(event.is(EventName.ATTACK_UNIT)){
            AttackUnitEvent attackUnitEvent = event.cast(AttackUnitEvent.class);
            Unit unit = attackUnitEvent.getUnit();
            if(unit.combat!=null) unit.combat.attack(attackUnitEvent.getTarget());
        }
        
        if(event.is(EventName.DEVELOP_CITY)) {
            DevelopCityEvent developCityEvent = event.cast(DevelopCityEvent.class);
            developCityEvent.getUnit().development.develop(developCityEvent.getCity());
        }

        if(event.is(EventName.TAKE_DAMAGE)) {
            TakeDamageEvent takeDamageEvent = event.cast();
            ShakeCamera shakeCamera = new ShakeCamera((OrthographicCamera) getCamera());
        }


//        if(event instanceof UnitEvent) {
//            this.dispatchEvent(event);
//        }

    }

//    private void dispatchEvent(Event event) {
//        for (Actor actor : this.getActors()) {
//            if(!(actor instanceof Unit)) continue;
//
//            UnitEvent ue = event.cast(UnitEvent.class);
//            Unit unit = ue.getSelf();
//
//            if(unit == actor) {
//                unit.handle(event);
//            }
//        }
//    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }

    private void updateCameraMovement(float delta) {
        getCamera().position.x += cameraMovement.x;
        getCamera().position.y += cameraMovement.y;

        cameraMovement.x = cameraMovement.x * 0.8f;
        cameraMovement.y = cameraMovement.y * 0.8f;
    }

    private void debug(ShapeRenderer render) {
        shapeRenderer.setProjectionMatrix(getCamera().combined);

        for (Map.Entry<String, DebugRenderer> stringDebugRendererEntry : debugRenderers.entrySet()) {
            DebugRenderer renderer = stringDebugRendererEntry.getValue();
            renderer.render(render);
        }
    }
}
