package com.workasintended.chromaggus;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameActor extends Actor {
	public final static int FACTION_NONE = 0;
	public final static int FACTION_PLAYER = 1;
	public final static int FACTION_ENEMY = 1<<1;
	
	protected int faction = FACTION_NONE;
	
	private float radius = 0;
	
	public int getFaction() {
		return faction;
	}
	
	public void setFaction(int faction) {
		this.faction = faction;
	}
	
	public Circle getCircle() {
		Circle a = new Circle(getX() + getOriginX(), getY() + getOriginY(), getRadius());
		return a;
	}
	
	public boolean overlap(GameActor actor) {
		Circle a = this.getCircle();
		Circle b = actor.getCircle();
		
		return Intersector.overlaps(a, b);
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
}
