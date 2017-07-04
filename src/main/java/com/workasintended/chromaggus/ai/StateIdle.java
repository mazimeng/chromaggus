package com.workasintended.chromaggus.ai;//package com.workasintended.chromaggus.ai;
//
//import com.badlogic.gdx.math.Vector2;
//import com.workasintended.chromaggus.Unit;
//import com.workasintended.chromaggus.pathfinding.Grid;
//
//import java.util.LinkedList;
//
//public class StateIdle extends AiComponent {
//	public StateIdle(Unit unit) {
//		super(unit);
//	}
//
//	@Override
//	public void update(float delta) {
//		super.update(delta);
//
//		LinkedList<Grid> watching = getUnit().getWorld().getGridMap().adjacents(getUnit().currentGrid, 3);
//		for (Grid grid : watching) {
//			if(grid.getUnits().size() == 0) continue;
//
//			Unit target = grid.getUnits().get(0);
//			if(target.getFaction() == getUnit().getFaction()) continue;
//
//			StateDefense stateDefense = new StateDefense(getUnit(), target);
//			getUnit().ai.setAiState(stateDefense);
//			break;
//		}
//	}
//
//
//	@Override
//	public void onEnter() {
//		System.out.format("idling%n");
//	}
//
//}
