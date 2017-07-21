package com.workasintended.chromaggus;

import java.util.List;

public interface UnitController {
	InputState getState();
	void setState(InputState state);
	
	WorldStage getStage();
	List<Unit> getUnits();
	void setUnits(List<Unit> units);
}
