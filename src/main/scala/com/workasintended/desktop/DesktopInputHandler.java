package com.workasintended.desktop;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.workasintended.chromaggus.*;
import com.workasintended.chromaggus.event.AttackUnitEvent;
import com.workasintended.chromaggus.event.DebugRendererArgument;
import com.workasintended.chromaggus.event.ShowCityWeaponEvent;
import com.workasintended.chromaggus.event.UnitSelectionEvent;
import com.workasintended.chromaggus.event.order.MoveToPositionEvent;

/**
 * Created by mazimeng on 1/15/16.
 */
public class DesktopInputHandler extends ActorGestureListener {
    private WorldStage worldStage;
    private InputHandler inputHandler;
    private Player player;

    public DesktopInputHandler(WorldStage worldStage, Player player) {
        this.worldStage = worldStage;
//        inputHandler = new Dialog(worldStage);
        inputHandler = new Selection();
        this.player = player;
    }

    @Override
    public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
        this.inputHandler.touchDown(event, x, y, pointer, button);
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        this.inputHandler.touchUp(event, x, y, pointer, button);
    }

    @Override
    public void tap(InputEvent event, float x, float y, int pointer, int button) {
        this.inputHandler.tap(event, x, y, pointer, button);

    }

    @Override
    public boolean longPress(Actor actor, float x, float y) {
//        System.out.println(String.format("longPress: %s, %s, %s", x, y, actor.toString()));

        return super.longPress(actor, x, y);
    }

    @Override
    public void fling(InputEvent event, float velocityX, float velocityY, int button) {
//        System.out.println(String.format("fling: %s, %s, %s", velocityX, velocityY, button));

    }

    @Override
    public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
        this.inputHandler.pan(event, x, y, deltaX, deltaY);
    }

    @Override
    public void zoom(InputEvent event, float initialDistance, float distance) {
        super.zoom(event, initialDistance, distance);
        this.inputHandler.zoom(event, initialDistance, distance);
    }

    @Override
    public void pinch(InputEvent event, Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
//        System.out.println(String.format("pinch: %s, %s, %s, %s",
//                initialPointer1, initialPointer2,
//                pointer1, pointer2));
    }

    public WorldStage getWorldStage() {
        return worldStage;
    }

    public void setWorldStage(WorldStage worldStage) {
        this.worldStage = worldStage;
    }

    public class InputHandler extends ActorGestureListener {
    }

    public class Selection extends InputHandler {
        Unit selected = null;

        public Selection() {
        }

        @Override
        public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
            Vector2 scrolling = new Vector2();
            if (selected != null) {
                Service.eventQueue().enqueue(new Event(EventName.SET_DEBUG_RENDERER,
                        new DebugRendererArgument("direction_" + selected.hashCode(),
                                new DebugRenderer.LineRenderer(selected.getX(Align.center), selected.getY(Align.center),
                                        x, y))));

//                scrolling = new Vector2(deltaX*0.25f, deltaY*0.25f);
            } else {
                scrolling = new Vector2(-deltaX, -deltaY);
            }
            Service.eventQueue().enqueue(new Event(EventName.MOVING_CAMERA, scrolling));
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (selected != null) {
                Unit selectedUnit = selected;
                selected = null;
                Actor actor = getWorldStage().hit(x, y, true);

                if(actor == null || !(actor instanceof Unit)) {
                    Service.eventQueue().enqueue(new MoveToPositionEvent(selectedUnit, new Vector2(x, y)));
                    return;
                }

                Unit unit = (Unit)actor;
                if (selectedUnit != unit) {
//                    if(unit.city==null &&
//                            !unit.getFaction().isFriend(selectedUnit.getFaction())) {
//                        Service.eventQueue().enqueue(new AttackUnitEvent(selectedUnit, (Unit) actor));
//                    }
//                    else if(unit.city!=null &&
//                            unit.getFaction().isFriend(selectedUnit.getFaction()) &&
//                            selectedUnit.development!=null) {
//                    }
                    if(unit.city==null) {
                        Service.eventQueue().enqueue(new AttackUnitEvent(selectedUnit, (Unit) actor));
                    }
                    else if(unit.city != null && selectedUnit.combat != null) {
                        //selectedUnit.combat.seize(unit);
                    }
                }
            }
        }

        @Override
        public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Actor actor = getWorldStage().hit(x, y, true);

            if (actor instanceof Unit) {
                Unit unit = (Unit) actor;
                if(unit.getFaction().isFriend(player.getFaction())) {
                    selected = (Unit) actor;
                    Service.eventQueue().enqueue(new UnitSelectionEvent(selected, true));
                }

            }
        }

        @Override
        public void tap(InputEvent event, float x, float y, int pointer, int button) {
            Actor actor = getWorldStage().hit(x, y, true);
            if (actor instanceof Unit) {
                Unit unit = (Unit)actor;
                if(unit.city!=null) {
                    Service.eventQueue().enqueue(new ShowCityWeaponEvent((Unit)actor, true));
                }
                Service.eventQueue().enqueue(new UnitSelectionEvent((Unit)actor, true));
            }

            if(actor == null) {
                Service.eventQueue().enqueue(new UnitSelectionEvent((Unit)actor, false));
            }
        }

        @Override
        public void zoom(InputEvent event, float initialDistance, float distance) {
//            this.selected = null;
//            int dir = distance > initialDistance ? 1 : -1;
//            Service.eventQueue().enqueue(new Event(EventName.CAMERA_ZOOM,
//                    new CameraZoomEvent(dir)));

        }
    }
}
