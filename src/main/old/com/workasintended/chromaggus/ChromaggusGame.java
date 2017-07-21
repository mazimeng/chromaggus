//package com.workasintended.chromaggus;
//
//import com.badlogic.gdx.Game;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.assets.AssetManager;
//import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//
//public class ChromaggusGame extends Game implements EventHandler{
//	private GameConfiguration gameConfiguration;
//
//	public ChromaggusGame(GameConfiguration gameConfiguration) {
//		Service.setEventQueue(new DefaultEventQueue());
//		Service.setAssetManager(new AssetManager());
//
//		this.gameConfiguration = gameConfiguration;
//	}
//
//	@Override
//	public void create() {
//		initAssets();
//		WorldScreen screen = new WorldScreen(gameConfiguration);
//		this.setScreen(screen);
//	}
//
//	@Override
//	public void render() {
//		float delta = Gdx.graphics.getDeltaTime();
//		Service.eventQueue().handle(delta);
//
//		super.render();
//	}
//
//	public void setGameConfiguration(GameConfiguration gameConfiguration) {
//		this.gameConfiguration = gameConfiguration;
//	}
//
//	private void initAssets() {
//		Service.assetManager().load("icon.png", Texture.class);
//		Service.assetManager().load("icon.06.png", Texture.class);
//		Service.assetManager().load("spritesheet/selection.png", Texture.class);
//
//
//		Service.assetManager().load("sound/level_up.wav", Sound.class);
//		Service.assetManager().load("sound/fireball.wav", Sound.class);
//		Service.assetManager().load("sound/melee.wav", Sound.class);
//		Service.assetManager().load("uiskin.json", Skin.class);
//		Service.assetManager().finishLoading();
//	}
//
//	@Override
//	public void handle(Event event) {
//		//listens to changing screen event
//	}
//}
