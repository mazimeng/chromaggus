package com.workasintended.chromaggus;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class ExecutionState extends InputState {
	private int command = -1;
	public ExecutionState(UnitController controller) {
		super(controller);
	}
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		return true;
	}

	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		
//		if(this.command == Keys.NUM_0) {
//			final float tx = x, ty = y;
//			Service.eventQueue().enqueue(new Event(EventName.MOVE, new MoveEventArgument(){
//				@Override
//				public Unit getUnit() {
//					return ExecutionState.this.getUnit();
//				}
//
//				@Override
//				public Vector2 getTarget() {
//					return new Vector2(tx, ty);
//				}
//
//			}));
//		}
		
		if(this.command == Keys.NUM_1) {
//			final Ability ability = this.getUnit().combat.getAbility(1);
//			final AbilityTarget t = new AbilityTarget();
//			final Actor victim = this.getStage().hit(x, y, false);
//			if(!(victim instanceof Unit)) return;
//			t.setUnit((Unit)victim);
//
//			Action action = new Action(){
//				@Override
//				public boolean act(float delta) {
//					ability.use(t);
//					return false;
//				}
//			};
//
//			if(ability.usable(t)){
//				this.getUnit().getActions().clear();
//				this.getUnit().addAction(action);
//			}
		}
		
		CommandingState state = new CommandingState(this.getController());
		this.getController().setState(state);
	}
	
	@Override
	public boolean keyUp(InputEvent event, int keycode) {
		if(keycode == Keys.ESCAPE) {
			CommandingState state = new CommandingState(this.getController());
			this.getController().setState(state);
		}
		else {
			command = keycode;
		}
		
		return true;
	}
}
