package com.workasintended.chromaggus.ability;//package com.workasintended.chromaggus.ability;
//
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.scenes.scene2d.Action;
//import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
//import com.badlogic.gdx.utils.Align;
//import com.workasintended.chromaggus.Unit;
//
//public class MeleeAttack implements Ability {
//	private float cooling = 0;
//	private float cooldown = 2.5f;
//	private Unit unit;
//	private float radius = 32;
//	private int power = 2;
//
//	public MeleeAttack(Unit unit) {
//		this.unit = unit;
//	}
//
//	protected boolean cooling() {
//		return this.cooling > 0;
//	}
//
//	@Override
//	public void update(float delta) {
//		if(this.cooling <= 0) {
//
//		}
//		else {
//			this.cooling -= delta;
//		}
//	}
//
//
//	@Override
//	public void use(AbilityTarget target) {
//		final AbilityTarget t = target;
//		Unit victim = (Unit)t.getUnit();
//		if(!this.usable(target)) return;
//
//		MeleeAttack.this.cooling = MeleeAttack.this.cooldown;
//		victim.hp -= MeleeAttack.this.unit.strength * this.power;
//
//		if(victim.hp <=0) {
//			System.out.println("target died");
//		}
//	}
//
//	@Override
//	public void stop() {
//
//	}
//
//	@Override
//	public boolean usable(AbilityTarget target) {
//		Unit victim = (Unit)target.getUnit();
//		if(victim == null) return false;
//		if(MeleeAttack.this.unit == victim) return false;
//		if(victim.dead()) return false;
//		if(MeleeAttack.this.cooling()) return false;
//		if((MeleeAttack.this.unit.getFaction() & victim.getFaction()) > 0) return false;
//
//		if(!inRange(target)) return false;
//
//		return true;
//	}
//
//	@Override
//	public boolean inRange(AbilityTarget target) {
//		if(target.getUnit() == null) return false;
//
//		Unit victim = (Unit)target.getUnit();
//		Vector2 pos = new Vector2(MeleeAttack.this.unit.getX(), MeleeAttack.this.unit.getY());
//		if(pos.dst2(victim.getX(), victim.getY()) > MeleeAttack.this.radius*MeleeAttack.this.radius) {
//			return false;
//		}
//		return true;
//	}
//}
