//package com.workasintended.chromaggus;
//
//import com.badlogic.gdx.scenes.scene2d.InputListener;
//import com.badlogic.gdx.scenes.scene2d.Event;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class HumanController extends InputListener implements UnitController {
//
//	private WorldStage stage;
//
//	private List<Unit> units = new ArrayList<>();
//	private InputState state;
//	public HumanController(WorldStage stage) {
//		this.stage = stage;
//		this.state = new CommandingState(this);
//		this.setState(this.state);
//	}
//
//	@Override
//	public boolean handle(Event e) {
//		return this.state.handle(e);
//	}
//
//	@Override
//	public void setState(InputState state) {
//		this.state = state;
//	}
//	@Override
//	public WorldStage getStage() {
//		return stage;
//	}
//	@Override
//	public List<Unit> getUnits() {
//		return units;
//	}
//
//	@Override
//	public void setUnits(List<Unit> units) {
//		this.units = units;
//	}
//	@Override
//	public InputState getState() {
//		return state;
//	}
//
//
////	@Override
////	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
////		return true;
////	}
////
////	@Override
////	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
////
////		Actor t = stage.hit(x, y, true);
////		Unit target = (t instanceof Unit)?(Unit)t:null;
////		if(command == null) {
////			if(this.actor != target) {
////				if(this.actor!=null) {
////					this.actor.selected = false;
////
////				}
////
////				if(target != null) {
////					target.selected = true;
////					this.state = State.Selected;
////				}
////				else {
////					this.state = State.Selecting;
////				}
////
////				this.actor = target;
////			}
////		}
////		else if(this.state == State.Selected && this.command == Command.MoveTo) {
////			float speed = 100;
////			Vector2 pos = new Vector2(this.actor.getX(Align.center), this.actor.getY(Align.center));
////			float d = pos.dst(x, y);
////			Abilities.MoveTo action = new Abilities.MoveTo();
////			action.setDuration(d/speed);
////			action.setPosition(x, y);
////			action.setActor(this.actor);
////			this.actor.getActions().clear();
////			this.actor.addAction(action);
////			command = null;
////			System.out.format("moving, arriving in %s sec\n", d/speed);
////		}
////		else if(state == State.Selected && command == Command.Attack) {
//////			MeleeAttack attack = new MeleeAttack();
//////			attack.setActor(this.actor);
//////			attack.setTarget(target);
//////			this.actor.getActions().clear();
//////			this.actor.addAction(attack);
//////			command = null;
//////			System.out.format("attacking\n");
////		}
////
////	}
////
////	@Override
////	public boolean keyUp(InputEvent event, int keycode) {
////		if(keycode == Keys.M) {
////			command = Command.MoveTo;
////			System.out.format("Command.MoveTo\n");
////		}
////		if(keycode == Keys.A) {
////			command = Command.Attack;
////		}
////
////		if(keycode == Keys.ESCAPE) {
////			command = null;
////		}
////
////		if(keycode == Keys.Q) {
////			System.out.format("state: %s, %s, %s\n", this.state, this.command, this.actor);
////		}
////		return true;
////	}
////
////	enum Command {
////	    MoveTo,
////	    Attack
////	}
////
////	enum State {
////		Selecting,
////		Selected
////	}
//}
