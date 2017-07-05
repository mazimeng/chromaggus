package com.workasintended.chromaggus.episode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.branch.DynamicGuardSelector;
import com.badlogic.gdx.ai.btree.branch.Parallel;
import com.badlogic.gdx.ai.btree.branch.Selector;
import com.badlogic.gdx.ai.btree.branch.Sequence;
import com.badlogic.gdx.ai.btree.decorator.Invert;
import com.badlogic.gdx.ai.btree.leaf.Wait;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibrary;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.workasintended.ai.BehaviorBuilder;
import com.workasintended.chromaggus.*;
import com.workasintended.chromaggus.ability.Fireball;
import com.workasintended.chromaggus.ability.Melee;
import com.workasintended.chromaggus.ai.AiComponent;
import com.workasintended.chromaggus.ai.behavior.*;
import com.workasintended.chromaggus.pathfinding.Grid;
import com.workasintended.chromaggus.pathfinding.GridMap;
import com.workasintended.chromaggus.unitcomponent.*;

import java.util.Iterator;

public class Episode01 {
    private Skin skin;
    public Unit lead1;
    public Unit lead2;

    public Episode01(Skin skin) {
        this.skin = skin;

        this.initBehaviorTree();
    }

    public void build(WorldStage stage, Table guiLayout) {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        BitmapFont font = new BitmapFont();
        font.getData().setScale(0.5f, 0.5f);

        Texture textureCursor = new Texture("cursor.png");
        Texture char00 = new Texture("char00.png");
        Texture char01 = new Texture("char01.png");
        Texture char02 = new Texture("char02.png");

        TextureRegion[][] char01Frames = TextureRegion.split(char01,
                char01.getWidth() / 3, char01.getHeight() / 4);
        TextureRegion[][] char00Frames = TextureRegion.split(char00,
                char00.getWidth() / 12, char00.getHeight() / 8);
        TextureRegion[][] char02Frames = TextureRegion.split(char02,
                char00.getWidth() / 12, char00.getHeight() / 8);
        Texture textureCity = textureCursor;

        {
            {
                Unit city = this.makeCity(stage, font, new TextureRegion(textureCity), Faction.FACTION_A);
                city.setPosition(14 * 32, 25 * 32);
                stage.addActor(city);
                makeCityArmory(city);
            }

            {
                Unit city = this.makeCity(stage, font, new TextureRegion(textureCity), Faction.FACTION_B);
                city.setPosition(200, 750);
                stage.addActor(city);
            }

            stage.getGridMap().grid(8, 7).state = Grid.State.Blocked;

            {
                TextureRegion[] frames = new TextureRegion[2];
                frames[0] = char01Frames[0][0];
                frames[1] = char01Frames[0][2];

                Unit unit = makeCharacter(stage, Faction.FACTION_A, font, frames);
                unit.setPosition(470, 760);
                stage.addActor(unit);
                lead1 = unit;
                unit.setName("lead1");
            }
            {
                TextureRegion[] frames = new TextureRegion[2];

                frames[0] = char00Frames[0][3];
                frames[1] = char00Frames[0][5];
                Unit unit = makeCharacter(stage, Faction.FACTION_A, font, frames);
                unit.setPosition(400, 815);
                stage.addActor(unit);
                lead2 = unit;
                unit.setName("lead2");
            }
            {
                TextureRegion[] frames = new TextureRegion[2];

                frames[0] = char00Frames[4][0];
                frames[1] = char00Frames[4][2];
                Unit unit = makeCharacter(stage, Faction.FACTION_B, font, frames);
                unit.setPosition(240, 720);
				unit.setSpeed(32);
//				unit.ai = new StateDefense(unit, stage);
                makeAi(unit);
                unit.combat.setStrength(32);
                unit.combat.setIntelligence(32);

                Melee melee = new Melee();
                melee.setPower(1.0f);
                Weapon weapon = makeSword(ActorFactory.instance().icon()[9][3], melee);
                unit.combat.setPrimaryWeapon(weapon);
//				unit.combat.setPrimaryWeapon(makeFireball());
                stage.addActor(unit);
            }

            {
                TextureRegion[] frames = new TextureRegion[2];

                frames[0] = char00Frames[4][3];
                frames[1] = char00Frames[4][5];
                Unit unit = makeCharacter(stage, Faction.FACTION_B, font, frames);
                unit.setPosition(768, 470);
                unit.combat.setStrength(12);
//				unit.ai = new StateDefense(unit, stage);

                stage.addActor(unit);
            }
            {
                TextureRegion[] frames = new TextureRegion[2];

                frames[0] = char00Frames[0][6];
                frames[1] = char00Frames[0][8];
                Unit unit = makeCharacter(stage, Faction.FACTION_B, font, frames);
                unit.setPosition(880, 380);
                unit.combat.setStrength(15);
//				unit.ai = new StateDefense(unit, stage);
                stage.addActor(unit);
            }
            {
                TextureRegion[] frames = new TextureRegion[2];

                frames[0] = char02Frames[0][0];
                frames[1] = char02Frames[0][2];
                Unit unit = makeCharacter(stage, Faction.FACTION_B, font, frames);
                unit.setPosition(870, 320);
//				unit.ai = new StateDefense(unit, stage);
                stage.addActor(unit);
            }

        }

        {

            TiledMap map = new TmxMapLoader(new InternalFileHandleResolver()).load("episode01.tmx");
            float unitScale = 2f;
            OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map, unitScale, stage.getBatch());
            stage.setTiledMapRenderer(renderer);

            TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
            MapObjects objects = layer.getObjects();
            layer.getCell(8, 25).getTile().getProperties();
            GridMap gridMap = stage.getGridMap();
            for (int i = 0; i < layer.getHeight(); ++i) {
                for (int j = 0; j < layer.getWidth(); ++j) {
                    TiledMapTileLayer.Cell cell = layer.getCell(j, i);
                    MapProperties mp = cell.getTile().getProperties();
                    Iterator<String> it = mp.getKeys();
                    if (mp.containsKey("state")) {
                        String value = mp.get("state").toString();
                        if (value.equals("blocked")) {
                            Grid grid = gridMap.grid(j, i);
                            grid.state = Grid.State.Blocked;
                        }
                    }
                }
            }
        }

        stage.getCamera().position.set(12 * 32f, 24 * 32f, 0);

    }

    protected Unit makeCharacter(WorldStage stage, Faction faction, BitmapFont font, TextureRegion[] frames) {
        Animation animation = new Animation(0.5f, frames);
        Unit unit = new Unit();

        CharacterRendererComponent characterRendererComponent = new CharacterRendererComponent(unit);
        characterRendererComponent.setAnimation(animation);

        unit.renderer = characterRendererComponent;

        unit.setTouchable(Touchable.enabled);
        unit.setFont(font);

        unit.setSize(32, 32);
        unit.setFaction(faction);

        MovementComponent movementComponent = new MovementComponent(unit);

        DevelopmentComponent developmentComponent = new DevelopmentComponent(unit);
        unit.development = developmentComponent;
        unit.movement = movementComponent;
        unit.combat = new CombatComponent(unit);
        unit.dialogComponent = new DialogComponent(unit, new Label("", skin));
        unit.inventory = new CityArmory.Inventory(2);
        unit.inventory.addListener(new EquipEventListener(unit));
        unit.setAbilityComponent(new AbilityComponent(unit));
        return unit;
    }

    protected void makeCityArmory(Unit city) {
        int slots = 3;

        CityArmory cityArmory = new CityArmory(slots, skin);
        city.city.setArmory(cityArmory);

        {
            Melee melee = new Melee();
            melee.setPower(1.0f);
            Weapon weapon = makeSword(ActorFactory.instance().icon()[9][3], melee);
            cityArmory.addCraft(weapon);

        }
        {
            Melee melee = new Melee();
            melee.setPower(2f);
            Weapon weapon = makeSword(ActorFactory.instance().icon()[24][5], melee);
            cityArmory.addCraft(weapon);

        }
        cityArmory.addCraft(makeFireball());
    }

    private Weapon makeSword(TextureRegion icon, Melee melee) {
        Weapon sword = new Weapon(new TextureRegionDrawable(icon), melee);
        return sword;
    }

    private Weapon makeFireball() {
        Weapon fireball = new Weapon(new TextureRegionDrawable(ActorFactory.instance().icon()[6][0]), new Fireball());
        return fireball;
    }


    protected Unit makeCity(WorldStage stage, BitmapFont font, TextureRegion texture, Faction faction) {
        Sprite sprite = new Sprite(texture);
        sprite.setSize(32, 32);


        Unit unitCity = new Unit();
        unitCity.setTouchable(Touchable.enabled);
        unitCity.setFaction(faction);

        CityComponent component = new CityComponent(unitCity);
        unitCity.city = component;
        unitCity.setSprite(sprite);
        return unitCity;
    }

    protected void makeAi(Unit unit) {
        Blackboard blackboard = new Blackboard();
        blackboard.setSelf(unit);

        BehaviorTreeLibrary library = BehaviorTreeLibraryManager.getInstance().getLibrary();

        BehaviorTree<Blackboard> tree = library.createBehaviorTree("selfDefense", blackboard);
        unit.ai = new AiComponent(unit, tree);

        tree.addListener(new BehaviorTree.Listener<Blackboard>() {
            @Override
            public void statusUpdated(Task<Blackboard> task, Task.Status previousStatus) {
                if (task.getStatus() == previousStatus) return;
//				System.out.println(String.format("tree updated: %s(%s), %s", task, task.getStatus(), previousStatus));
            }

            @Override
            public void childAdded(Task<Blackboard> task, int index) {

            }
        });

        BehaviorTreeViewer behaviorTreeViewer = new BehaviorTreeViewer(tree, skin);
        unit.ai.setDebugger(behaviorTreeViewer);
    }

    private void initBehaviorTree() {
        BehaviorTreeLibraryManager libraryManager = BehaviorTreeLibraryManager.getInstance();
        BehaviorTreeLibrary library = new BehaviorTreeLibrary(BehaviorTreeParser.DEBUG_HIGH);
        libraryManager.setLibrary(library);


        {

            Task<Blackboard> defense = makeDefense();
            Task<Blackboard> develop = makeDevelop();
            Task<Blackboard> seize = makeSeize();
            Task<Blackboard> guard = BehaviorBuilder.makeGuard();


//            BehaviorTree<Blackboard> tree = new BehaviorTree<Blackboard>(new Selector<>(defense, new Sequence<>(new Wait(3), new Powerful(), seize)));
            BehaviorTree<Blackboard> tree = new BehaviorTree<Blackboard>();
            tree.addChild(guard);
            library.registerArchetypeTree("selfDefense", tree);
        }
    }

    private Task<Blackboard> makeDevelop() {
        Sequence<Blackboard> findThreatSequence = new Sequence<>();
        findThreatSequence.addChild(new ScanThreat());
        findThreatSequence.addChild(new TargetEnemy());

        Sequence<Blackboard> attackGuardSequence = new Sequence<>();
        attackGuardSequence.addChild(new TargetExists());
        attackGuardSequence.addChild(new TargetIsAThreat());
        AttackTarget attackTarget = new AttackTarget();
        attackTarget.setGuard(attackGuardSequence);

        DynamicGuardSelector<Blackboard> attackSelector = new DynamicGuardSelector<>();
        attackSelector.addChild(attackTarget);
        attackSelector.addChild(new StopEverything());


        Sequence<Blackboard> findCity = new Sequence<>();
        findCity.addChild(new FindClosestOurCity());

        Sequence<Blackboard> moveToCityGuardSequence = new Sequence<>();
        moveToCityGuardSequence.addChild(new Invert<Blackboard>(new TargetWithinRadius(32)));

        MoveToTarget moveToCity = new MoveToTarget();
        moveToCity.setGuard(moveToCityGuardSequence);

        DevelopCity developCity = new DevelopCity();
        developCity.setGuard(new Sequence<Blackboard>(new Invert<Blackboard>(new ScanThreat()), new Invert<Blackboard>(new TargetWithinRadius(32))));
        Sequence<Blackboard> developSequence = new Sequence<>(new FindClosestOurCity(), new DynamicGuardSelector<>(developCity));
        return developSequence;
    }

    private Task<Blackboard> makeDefense() {
        Parallel<Blackboard> parallelSelector = new Parallel<>(Parallel.Policy.Selector);
        Sequence<Blackboard> findThreatSequence = new Sequence<Blackboard>();
        findThreatSequence.addChild(new ScanThreat());
        findThreatSequence.addChild(new TargetEnemy());

        Sequence<Blackboard> attackGuardSequence = new Sequence<>();
        attackGuardSequence.addChild(new TargetExists());
        attackGuardSequence.addChild(new TargetIsAThreat());

        AttackTarget attackTarget = new AttackTarget();


        parallelSelector.addChild(new Wait<Blackboard>(3));
        parallelSelector.addChild(attackTarget);
        Sequence<Blackboard> returnSequence = new Sequence<>();
        returnSequence.addChild(new Selector(new TargetIsDead(), new TooFarAwayFromCity()));
        returnSequence.addChild(new FindClosestOurCity());
        returnSequence.addChild(new MoveToTarget());

        Sequence<Blackboard> defenseSequence = new Sequence<>(findThreatSequence, parallelSelector, returnSequence);

        return defenseSequence;
    }

    private Task<Blackboard> makeSeize() {

        //kill anything within range of enemy's city
        {
            Parallel<Blackboard> parallelSelector = new Parallel<>(Parallel.Policy.Selector);
            Sequence<Blackboard> findThreatSequence = new Sequence<Blackboard>();
            findThreatSequence.addChild(new ScanThreat());
            findThreatSequence.addChild(new TargetEnemy());

            Sequence<Blackboard> attackGuardSequence = new Sequence<>();
            attackGuardSequence.addChild(new TargetExists());
            attackGuardSequence.addChild(new TargetIsAThreat());

            AttackTarget attackTarget = new AttackTarget();

            parallelSelector.addChild(new Wait<Blackboard>(3));
            parallelSelector.addChild(attackTarget);

            Sequence<Blackboard> returnSequence = new Sequence<>();
//			returnSequence.addChild(new Selector(new TargetIsDead(), new TooFarAwayFromEnemyCity()));
            returnSequence.addChild(new FindClosestEnemyCity());
            returnSequence.addChild(new MoveToTarget());

            Sequence<Blackboard> clear = new Sequence<>(returnSequence);
            return clear;
        }
    }

    private Task<Blackboard> makeGuardCurrentPosition() {
        Parallel<Blackboard> attackSelector = new Parallel<>(Parallel.Policy.Selector);
        Sequence<Blackboard> findThreatSequence = new Sequence<Blackboard>();
        findThreatSequence.addChild(new ScanThreat());
        findThreatSequence.addChild(new TargetEnemy());

        Sequence<Blackboard> attackGuardSequence = new Sequence<>();
        attackGuardSequence.addChild(new TargetExists());
        attackGuardSequence.addChild(new TargetIsAThreat());

        AttackTarget attackTarget = new AttackTarget();


        attackSelector.addChild(new Wait<Blackboard>(3));
        attackSelector.addChild(attackTarget);

        Selector<Blackboard> returnSelector = new Selector<>();

        returnSelector.addChild(new WithinRadius(new GetPosition() {
            @Override
            public Vector2 get(Blackboard blackboard) {

                return blackboard.getStationPosition();
            }
        }, 64));

        returnSelector.addChild(new MoveToPosition(new GetPosition() {
            @Override
            public Vector2 get(Blackboard blackboard) {
                return blackboard.getStationPosition();
            }
        }, 32f));

        Sequence<Blackboard> guardSequence = new Sequence<>(
                returnSelector,
                new Sequence<>(findThreatSequence, attackSelector));

        return new Parallel<Blackboard>(Parallel.Policy.Selector
                , new GuardCurrentArea()
                , new Wait<Blackboard>(3));
    }

    private Task<Blackboard> makeGuard() {
        LeafTask<Blackboard> markPosition = new MarkStation();
        Sequence<Blackboard> findThreatSequence = new Sequence<Blackboard>();
        findThreatSequence.addChild(new ScanThreat(64));
        findThreatSequence.addChild(new TargetEnemy());

        {
            WithinRadius withinRadius = new WithinRadius(new GetPosition() {
                @Override
                public Vector2 get(Blackboard blackboard) {
                    return blackboard.getStationPosition();
                }
            }, 64);
            findThreatSequence.addChild(withinRadius);
        }

        AttackTarget attackTarget = new AttackTarget();


        DynamicGuardSelector<Blackboard> attack = new DynamicGuardSelector<>();
        attackTarget.setGuard(findThreatSequence);

        StopEverything stopEverything = new StopEverything();
        stopEverything.setGuard(new Invert<Blackboard>(new ScanThreat(64)));

        attack.addChild(attackTarget);

        WithinRadius withinRadius = new WithinRadius(new GetPosition() {
            @Override
            public Vector2 get(Blackboard blackboard) {
                return blackboard.getStationPosition();
            }
        }, 8);
        MoveToPosition moveToPosition = new MoveToPosition(new GetPosition() {
            @Override
            public Vector2 get(Blackboard blackboard) {
                return blackboard.getStationPosition();
            }
        }, 8);

        moveToPosition.setGuard(new Invert<Blackboard>(withinRadius));

        DynamicGuardSelector<Blackboard> returnToStation = new DynamicGuardSelector<>();
        returnToStation.addChild(moveToPosition);

        return new Sequence<>(markPosition, new Selector<>(
                attack, returnToStation
        ));

//        return new Parallel<>(Parallel.Policy.Selector, new GuardCurrentArea(), new AlwaysFail<>(dynamicGuardSelector), new AlwaysFail<>(moveToPosition));
//        return new Parallel<>(Parallel.Policy.Selector, new GuardCurrentArea(), new AlwaysFail<>(dynamicGuardSelector));
    }

}