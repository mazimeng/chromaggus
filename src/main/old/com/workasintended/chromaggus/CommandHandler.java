//package com.workasintended.chromaggus;
//
//import com.workasintended.chromaggus.pathfinding.Grid;
//
//import java.util.Hashtable;
//import java.util.LinkedList;
//
//public class CommandHandler implements EventHandler {
//	private WorldStage stage;
//	private Hashtable<Unit, LinkedList<Grid>> paths = new Hashtable<>();
//
//	public CommandHandler(WorldStage stage) {
//		super();
//		this.stage = stage;
//	}
//
//	@Override
//	public void handle(Event event) {
//		if(event.getName() == EventName.MOVE) {
////			MoveEventArgument arg = event.getArgument(MoveEventArgument.class);
////			List<Unit> units = arg.getUnits();
////
////			Actor actor = this.stage.hit(arg.getTarget().x, arg.getTarget().y, false);
////			boolean chase = actor instanceof Unit;
////
////			units.forEach(unit -> {
////				if (unit.movement == null) return;
////
////				if(chase) {
////					unit.setOrder(new Attack(unit, (Unit)actor));
////				}
////				else {
////					unit.setOrder(new Move(unit, arg.getTarget()));
////				}
////			});
//		}
//	}
//
//}
