package com.workasintended.chromaggus;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.workasintended.chromaggus.ability.Ability;
import com.workasintended.chromaggus.ai.AiComponent;
import com.workasintended.chromaggus.event.DevelopCityEvent;
import com.workasintended.chromaggus.order.Idle;
import com.workasintended.chromaggus.order.Order;
import com.workasintended.chromaggus.pathfinding.Grid;
import com.workasintended.chromaggus.unitcomponent.*;

public class Unit extends Group {
    public float radius = 32;
    public float speed = 32;
    private Faction faction;


    public Sprite highlight;

    private Sprite sprite;
    private boolean highlighted = false;

    private BitmapFont font;

    public AiComponent ai;
    public CombatComponent combat;
    public CityComponent city;
    public MovementComponent movement;
    public CharacterRendererComponent renderer;
    public DevelopmentComponent development;

    public DialogComponent dialogComponent;
    public CityArmory.Inventory inventory;
    private AbilityComponent abilityComponent;

    private Order order = new Idle();

    public Ability[] abilities;

    public Grid currentGrid;

    public Unit() {
        abilities = new Ability[9];
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.setSize(sprite.getWidth(), sprite.getHeight());
        //this.setOrigin(sprite.getWidth() * 0.5f, sprite.getHeight() * 0.5f);
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    @Override
    public void act(float delta) {
        if (this.dialogComponent != null) this.dialogComponent.update(delta);
        if (this.dead()) {
            Service.eventQueue().enqueue(new Event(EventName.UNIT_DIED, this));
            return;
        }
        super.act(delta);

        if (this.city != null) this.city.update(delta);
        if (this.ai != null) this.ai.update(delta);
        if (this.combat != null) this.combat.update(delta);

        if (this.order != null) this.order.update(delta);
        if (this.development != null) this.development.update(delta);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {


        if (this.renderer != null) {
            renderer.draw(batch, parentAlpha);
        }

        if (this.sprite != null) batch.draw(sprite
                , this.getX(), this.getY()
                , this.getOriginX(), this.getOriginY()
                , this.getWidth(), this.getHeight()
                , this.getScaleX(), this.getScaleY()
                , this.getRotation());

        if (font != null && combat != null) {
            float cooldown = combat.getPrimaryAbility()==null?
                    0f: combat.getPrimaryAbility().getCooldownProgress();
            float cast = combat.getPrimaryAbility()==null?
                    0f: combat.getPrimaryAbility().getCooldownProgress();
            String state = String.format("%s/%s, %.0f, %.0f",
                    combat.getHp(),
                    combat.getMaxHp(),
                    cooldown,
                    cast);
            font.draw(batch, state, this.getX(), this.getY());
        }
        if (this.city != null) this.city.draw(batch);

        if (this.highlight != null) batch.draw(highlight
                , this.getX(), this.getY()
                , this.getOriginX(), this.getOriginY()
                , this.getWidth(), this.getHeight()
                , this.getScaleX(), this.getScaleY()
                , 0);

        super.draw(batch, parentAlpha);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean dead() {
        return this.combat != null && this.combat.getHp() <= 0;
    }

    public WorldStage getWorld() {
        Stage stage = this.getStage();
        WorldStage world = (stage instanceof WorldStage) ? (WorldStage) stage : null;
        return world;
    }

    public void queueAction(Action action) {
        SequenceAction sequenceAction = null;
        if (getActions().size == 0) {
            sequenceAction = new SequenceAction();
            getActions().add(sequenceAction);
        }

        sequenceAction.addAction(action);
    }

    public void setOrder(Order order) {
        final Order o = order;
//		this.order.setOnStop(new Runnable() {
//			@Override
//			public void run() {
//				Unit.this.order = o;
//				if (Unit.this.order != null) {
//					Unit.this.order.start();
//				}
//			}
//		});
        //this.order.stop();
        this.order = o;
        this.order.start();
    }

    public void occupy(Grid grid) {
        grid.addUnit(this);
        grid.state = Grid.State.Blocked;
        currentGrid = grid;
    }

    public void release(Grid grid) {
        grid.removeUnit(this);
        grid.state = Grid.State.Walkable;
    }

    public void occupy(float x, float y) {
        Grid grid = getWorld().getGridMap().grid(x, y);
        occupy(grid);

    }

    public void release(float x, float y) {
        Grid grid = getWorld().getGridMap().grid(x, y);
        release(grid);
    }

    public void handle(Event event) {
//        if (handleAttack(event)) {
//        }
//        else if(handleMoveToPosition(event)) {
//
//        }
//        else if (event.getName() == EventName.MOVE_UNIT) {
//            MoveUnitArgument moveUnitArgument = event.getArgument(MoveUnitArgument.class);
//            if (moveUnitArgument.getUnit() != this) return;
//
//            Actor actor = this.getStage().hit(moveUnitArgument.getTarget().x, moveUnitArgument.getTarget().y, false);
//            if (actor == this) return;
//
//            Unit targetUnit = null;
//            if (actor instanceof Unit) {
//                targetUnit = (Unit) actor;
//            }
//
//            Action action = null;
//
//            if (targetUnit == null) {
//                action = new MoveToPosition(moveUnitArgument.getTarget());
//            } else if (targetUnit.city != null && this.development != null) {
//                Unit city = targetUnit;
//                ParallelAction parallelAction = new ParallelAction(new MoveToUnit(city),
//                        new Develop(city));
//                action = parallelAction;
//            }
//
//            this.clearActions();
//            this.addAction(action);
//        }

    }

//    private boolean handleMoveToPosition(Event event) {
//        if(!event.is(EventName.MOVE_TO_POSITION)) return false;
//
//        MoveToPositionEvent moveToPositionEvent = event.cast(MoveToPositionEvent.class);
//
//        Action action = new MoveToPosition(moveToPositionEvent.getPosition());
//        this.clearActions();
//        this.addAction(action);
//        return true;
//    }

//    private boolean handleAttack(Event event) {
//        if(!event.is(EventName.ATTACK_UNIT)) return false;
//
//        AttackUnitEvent attackUnitEvent = event.cast(AttackUnitEvent.class);
//
//        Unit target = attackUnitEvent.getTarget();
//        Unit attacker = attackUnitEvent.getAttacker();
//        if(attacker!=this) return false;
//        if (target.combat == null || attacker.combat==null) return false;
//
//        SequenceAction sequenceAction = new SequenceAction(new MoveToUnit(target), new com.workasintended.chromaggus.action.Attack(target));
//        RepeatAction repeatAction = new RepeatAction();
//        repeatAction.setAction(sequenceAction);
//        repeatAction.setCount(RepeatAction.FOREVER);
//        Action action = repeatAction;
//        this.clearActions();
//        this.addAction(action);
//
//        return true;
//    }

    private boolean handleDevelop(Event event) {
        if (!(event instanceof DevelopCityEvent)) return false;

        return true;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public AbilityComponent getAbilityComponent() {
        return abilityComponent;
    }

    public void setAbilityComponent(AbilityComponent abilityComponent) {
        this.abilityComponent = abilityComponent;
    }
}
