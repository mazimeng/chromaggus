package com.workasintended.chromaggus;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.workasintended.chromaggus.event.MoveUnitArgument;
import com.workasintended.chromaggus.event.SelectionCompleted;
import com.workasintended.chromaggus.pathfinding.Grid;

import java.util.LinkedList;
import java.util.List;

public class CommandingState extends InputState {
    public CommandingState(UnitController controller) {
        super(controller);
    }

    private Rectangle selectionBox;
    private Vector2 selectionBoxPivot;
    private boolean selecting = false;

    private boolean movingCamera = false;
    private Vector2 lastPointerPosition = new Vector2();

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        super.touchDragged(event, x, y, pointer);

        if (selecting) {
            selectionBox.set(Math.min(x, selectionBoxPivot.x), Math.min(y, selectionBoxPivot.y),
                    Math.abs(x - selectionBoxPivot.x), Math.abs(y - selectionBoxPivot.y));
            Service.eventQueue().enqueue(new Event(EventName.SELECTING, selectionBox));
        }
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (button == Buttons.LEFT) {
            selectionBoxPivot = new Vector2(x, y);
            selectionBox = new Rectangle(selectionBoxPivot.x, selectionBoxPivot.y, 1, 1);
            Service.eventQueue().enqueue(new Event(EventName.SELECTION_STARTED, selectionBox));

            selecting = true;
        }

        if (button == Buttons.MIDDLE) {
            movingCamera = true;
            lastPointerPosition.x = x;
            lastPointerPosition.y = y;
        }

        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (button == Buttons.LEFT) {
            selecting = false;
            List<Unit> characters = selectCharacters();
            getController().setUnits(characters);
            Service.eventQueue().enqueue(new SelectionCompleted(characters));

            Grid grid = getStage().getGridMap().grid(x, y);
            System.out.format("click %s%n", grid);
        }

        if (button == Buttons.RIGHT && this.getController().getUnits().size() > 0) {
            final float tx = x, ty = y;

            List<Unit> units = this.getController().getUnits();
            for (Unit unit : units) {
                Service.eventQueue().enqueue(new Event(EventName.MOVE_UNIT, new MoveUnitArgument(unit, new Vector2(tx, ty))));
            }
        }

        if (button == Buttons.MIDDLE) {
            movingCamera = false;
        }
    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        Vector2 scrolling = new Vector2();
        float zone = 10;
        float halfWidth = getStage().getViewport().getWorldWidth() * 0.5f;
        float halfHeight = getStage().getViewport().getWorldHeight() * 0.5f;
        Vector3 cameraPosition = getStage().getCamera().position;
        float west = cameraPosition.x - halfWidth + zone;
        float east = cameraPosition.x + halfWidth - zone;
        float north = cameraPosition.y + halfHeight - zone;
        float south = cameraPosition.y - halfHeight + zone;

        if (x <= west) {
            scrolling.x = -1;
        }
        if (x >= east) {
            scrolling.x = 1;
        }

        if (y >= north) {
            scrolling.y = 1;
        }
        if (y <= south) {
            scrolling.y = -1;
        }

        Service.eventQueue().enqueue(new Event(EventName.MOVING_CAMERA, scrolling));
        return super.mouseMoved(event, x, y);
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
//		boolean execute = false;
//		if(keycode >= Keys.NUM_0 && keycode <= Keys.NUM_1) {
//			execute = true;
//		}
//
//		execute = execute && (this.getUnit() != null);
//		
//		if(execute) {
//			ExecutionState state = new ExecutionState(this.getController());
//			this.getController().setState(state);
//			state.handle(event);
//		}

        return true;
    }

    private LinkedList<Unit> selectCharacters() {
        LinkedList<Unit> units = new LinkedList<>();

        for (Actor actor : getStage().getActors()) {
            if (!(actor instanceof Unit)) continue;

            Rectangle boundingBox = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());

            if (selectionBox.overlaps(boundingBox)) {
                units.add((Unit)actor);
            }
        }

        return units;
    }
}
