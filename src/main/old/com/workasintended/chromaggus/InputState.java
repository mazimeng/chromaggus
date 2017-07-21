package com.workasintended.chromaggus;

import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.List;

public class InputState extends InputListener {
	private UnitController controller;
	
	public InputState(UnitController controller) {
		this.controller = controller;
	}
	
	public UnitController getController() {
		return this.controller;
	}
	
	public List<Unit> getUnits() {
		return this.controller.getUnits();
	}
	
	public WorldStage getStage() {
		return this.controller.getStage();
	}
}
