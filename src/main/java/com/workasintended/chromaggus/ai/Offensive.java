package com.workasintended.chromaggus.ai;//package com.workasintended.chromaggus.ai;
//
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.scenes.scene2d.Action;
//import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.utils.Align;
//import com.badlogic.gdx.utils.Array;
//import com.workasintended.chromaggus.Unit;
//import com.workasintended.chromaggus.WorldStage;
//
//import java.util.LinkedList;
//
//public class Offensive extends AiComponent {
//    private float alertRadius = 96f;
//    private Vector2 station;
//    private Unit target;
//
//    public Offensive(AiComponent previous) {
//        super(previous);
//    }
//
//    public Offensive(Unit self, WorldStage stage) {
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
//                chase(t);
//                break;
//            }
//        }
//    }
//
//    private void chase(Unit target) {
//        StateOffense stateOffense = new StateOffense(this, target){
//            @Override
//            protected boolean exit() {
//                getSelf().ai = Offensive.this;
//                return true;
//            }
//        };
//
//        getSelf().ai = stateOffense;
//    }
//}
