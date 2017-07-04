package com.workasintended.chromaggus.ai;//package com.workasintended.chromaggus.ai;
//
//import com.badlogic.gdx.math.Vector2;
//import com.workasintended.chromaggus.Service;
//import com.workasintended.chromaggus.event.AttackUnitEvent;
//import com.workasintended.chromaggus.event.order.MoveToPositionEvent;
//
//public class StateReturn extends AiComponent {
//	private Vector2 returnTo;
//
//    public StateReturn(AiComponent previous, Vector2 returnTo) {
//        super(previous);
//        this.returnTo = returnTo;
//
//        Service.eventQueue().enqueue(new MoveToPositionEvent(getSelf(), returnTo));
//    }
//
//	@Override
//	public void update(float delta) {
//        if(getSelf().getActions().size == 0) {
//            arrived();
//        }
//	}
//
//    protected void arrived() {
//        getSelf().ai = new StateDefense(this);
//    }
//
//}
