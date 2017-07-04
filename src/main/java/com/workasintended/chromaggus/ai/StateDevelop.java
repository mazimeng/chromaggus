package com.workasintended.chromaggus.ai;//package com.workasintended.chromaggus.ai;
//
//import com.badlogic.gdx.math.Vector2;
//import com.workasintended.chromaggus.*;
//
//public class StateDevelop extends StateDefense {
//	public StateDevelop(Unit unit, Unit target) {
//		super(unit, target);
//	}
//
////	public StateDevelop(Unit city, Unit unit) {
////		super(city, unit);
////	}
////
////	@Override
////	public void update(float delta) {
////		Unit intruder = this.closestUnit(this.city, this.responseRange);
//////		if(intruder != null) {
//////			final float tx = intruder.getX(),
//////					ty = intruder.getY();
//////			Service.eventQueue().enqueue(new Event(EventName.MOVE, new MoveEventArgument(){
//////				@Override
//////				public Unit getUnit() {
//////					return StateDevelop.this.unit;
//////				}
//////
//////				@Override
//////				public Vector2 getTarget() {
//////					return new Vector2(tx, ty);
//////				}
//////			}));
//////
//////			System.out.println("entering defense state");
//////			StateDefense state = new StateDefense(this.city, this.unit);
//////			this.unit.ai = state;
//////		}
////	}
//}
