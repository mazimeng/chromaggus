package com.workasintended.chromaggus.ability;

import com.badlogic.gdx.math.Vector2;
import com.workasintended.chromaggus.Unit;

public class AbilityTarget {
	private Vector2 location;
	
	private Unit unit;
	
	public AbilityTarget() {
		
	}
		
	public AbilityTarget(Vector2 location, Unit unit) {
		super();
		this.location = location;
		this.unit = unit;
	}

	public AbilityTarget(Unit unit) {
		super();
		this.unit = unit;
	}
	
	public Vector2 getLocation() {
		return location;
	}
	public void setLocation(Vector2 location) {
		this.location = location;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
}
