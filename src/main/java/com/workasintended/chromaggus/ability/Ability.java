package com.workasintended.chromaggus.ability;

import com.workasintended.chromaggus.Unit;

public interface Ability {
	void update(float delta);
	float getCastingTime();
	void use();
	boolean inRange();
	boolean cast(float delta);
	void reset();
	void setTarget(Unit target);
	void setUser(Unit unit);
	float getCastRange();
	void effect();
	float getCooldownProgress();
	float getCastingProgress();
	void setCastingProgress(float castingProgress);
	boolean isCasting();
	boolean stop();
	Ability clone();
	float getCooldown();

}