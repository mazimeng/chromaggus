package com.workasintended.chromaggus.ai;//package com.workasintended.chromaggus.ai;
//
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.utils.Align;
//import com.badlogic.gdx.utils.Array;
//import com.workasintended.chromaggus.Unit;
//import com.workasintended.chromaggus.WorldStage;
//
//public class StateDefense extends AiComponent {
//    private float alertRadius = 96f;
//    private Vector2 station;
//    private Unit target;
//
//    public StateDefense(AiComponent previous) {
//        super(previous);
//    }
//
//    public StateDefense(Unit self, WorldStage stage) {
//        super(self, stage);
//    }
//
//
//    @Override
//	public void update(float delta) {
//		Array<Actor> actors = getStage().getActors();
//
//        for (Actor actor : actors) {
//            if(!(actor instanceof Unit) || getSelf() == actor) continue;
//
//            Unit t = (Unit)actor;
//            if(t.dead()) continue;
//            if((t.getFaction().isFriend(getSelf().getFaction()))) continue;
//
//            if(Vector2.dst2(getSelf().getX(Align.center), getSelf().getY(Align.center),
//                    t.getX(Align.center), t.getY(Align.center)) <= alertRadius*alertRadius) {
//
//                getSelf().ai = new StateOffense(this, t);
//                break;
//            }
//        }
//
//    }
//}
