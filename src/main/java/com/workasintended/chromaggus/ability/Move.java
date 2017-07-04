package com.workasintended.chromaggus.ability;//package com.workasintended.chromaggus.ability;
//
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.utils.Align;
//import com.workasintended.chromaggus.Unit;
//import com.workasintended.chromaggus.WorldStage;
//
//public class Move implements Ability {
//	private AbilityTarget target;
//	private Unit unit;
//	private float reachRange = 8;
//
//	public Move(Unit unit) {
//		super();
//		this.unit = unit;
//	}
//
//	@Override
//	public void update(float delta) {
//		if(this.target == null) return;
//
//		MeleeAttack melee = (MeleeAttack)this.unit.combat.getAbility(Ability.ATTACK);
//		if(melee.inRange(this.target)) {
//			melee.use(target);
//		}
//		else {
//			Vector2 pos = this.target.getUnit()==null?this.target.getLocation():
//				new Vector2(this.target.getUnit().getX()
//						, this.target.getUnit().getY());
//			this.move(pos, delta);
//		}
//
//	}
//
//	protected void move(Vector2 to, float delta) {
//		Vector2 from = new Vector2(this.unit.getX(), this.unit.getY());
//		Vector2 velocity = new Vector2(to).sub(from);
//
//		Vector2 pos = velocity
//				.nor()
//				.scl(this.unit.getSpeed() * delta)
//				.add(from);
//
//		this.unit.setPosition(pos.x, pos.y);
//	}
//
//	@Override
//	public void use(AbilityTarget target) {
//		this.target = target;
//		WorldStage world = this.unit.getWorld();
//		Actor actor = world.hit(target.getLocation().x, target.getLocation().y, false);
//		if(actor instanceof Unit) {
//			this.target.setUnit((Unit)actor);
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
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean inRange(AbilityTarget target) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//}
