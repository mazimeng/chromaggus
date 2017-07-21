package com.workasintended.chromaggus.unitcomponent;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.workasintended.chromaggus.CityArmory;
import com.workasintended.chromaggus.Faction;
import com.workasintended.chromaggus.Service;
import com.workasintended.chromaggus.Unit;
import com.workasintended.chromaggus.event.GainGoldEvent;

import java.util.HashSet;
import java.util.Set;

public class CityComponent extends UnitComponent {
    private float radius = 64;
    private BitmapFont font;

    private float development = 0;
    private float incomeInterval = 3;
    private float progress = 0;

    private float restoreCooldown = 3;
    private float restore = 0;
    private CityArmory armory;

    private float restoreRate = 0.2f;
    private float restoreRadius2 = 64f * 64f;
    private float seizeRadius2 = 64f * 64f;
    private float seizeTime = 5f; //5 secs
    private float seizeCount = 0f;
    private Set<Faction> factionSet = new HashSet<>();

    public CityComponent(Unit self) {
        super(self);
    }

//	public CityComponent(WorldStage stage, Unit unit, BitmapFont font) {
//		this.unit = unit;
//		//this.gold = new Attribute<Float>(0f, 100f);
//		this.font = font;
//		this.stage = stage;
//	}

    public String printFaction() {
        return String.format("cityFaction: #%s, %s/%s", getSelf().getFaction().getValue(), seizeCount, seizeTime);
    }

    public void update(float delta) {
        restore += delta;

        tax(delta);
        for (Actor actor : getSelf().getWorld().getActors()) {
            if (actor == getSelf()) continue;
            if (!(actor instanceof Unit)) continue;

            Unit unit = (Unit) actor;
            restore(delta, unit);
            faction(unit);
        }

        computeFaction(delta);

        if (restore >= restoreCooldown) {
            restore = 0;
        }
    }

    public void draw(Batch batch) {
//		if(font!=null) font.draw(batch, String.format("%.1f/%.1f"
//				, this.gold.getValue(), this.gold.getMax())
//				, this.unit.getX(), this.unit.getY());
    }

    private void tax(float delta) {
        progress += delta;
        if (progress > incomeInterval) {
            progress = 0;
            float gold = development;
            Service.eventQueue().enqueue(new GainGoldEvent(getSelf(), gold));
        }
    }

    private void restore(float delta, Unit unit) {
        if (!unit.getFaction().isFriend(getSelf().getFaction())) return;
        if (unit.city != null) return;
        if (unit.combat == null) return;
        if (unit.combat.getHp() == unit.combat.getMaxHp()) return;
        if (unit.dead()) return;

        if (Vector2.dst2(unit.getX(Align.center), unit.getY(Align.center),
                getSelf().getX(Align.center), getSelf().getY(Align.center)) > restoreRadius2) return;

        if (restore < restoreCooldown) return;

        unit.combat.setHp((int) (unit.combat.getHp() * (restoreRate + 1f)));
    }

    private void faction(Unit unit) {
        if (Vector2.dst2(unit.getX(Align.center), unit.getY(Align.center),
                getSelf().getX(Align.center), getSelf().getY(Align.center)) > seizeRadius2) return;

        if (unit.dead()) return;
        factionSet.add(unit.getFaction());
    }

    private void computeFaction(float delta) {
        if (factionSet.size() == 1) {
            Faction faction = factionSet.iterator().next();
            if (faction != getSelf().getFaction()) {
                seizeCount += delta;

                if (seizeCount >= seizeTime) {
                    System.out.println(String.format("faction changed: #%s->#%s", getSelf().getFaction().getValue(), faction.getValue()));
                    getSelf().setFaction(faction);
                    seizeCount = 0;
                }
            } else {
                seizeCount = 0;
            }
        } else {
            seizeCount = 0;
        }

        factionSet.clear();
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getDevelopment() {
        return development;
    }

    public void setDevelopment(float development) {
        this.development = development;
    }

    public CityArmory getArmory() {
        return armory;
    }

    public void setArmory(CityArmory armory) {
        this.armory = armory;
    }
}
