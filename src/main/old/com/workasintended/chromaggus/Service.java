package com.workasintended.chromaggus;

import com.badlogic.gdx.assets.AssetManager;

public class Service {
	private static EventQueue eventQueue;
	private static AssetManager assetManager;

	public static EventQueue eventQueue() {
		return eventQueue;
	}

	public static void setEventQueue(EventQueue eventQueue) {
		Service.eventQueue = eventQueue;
	}

	public static AssetManager assetManager() {
		return assetManager;
	}

	public static void setAssetManager(AssetManager assetManager) {
		Service.assetManager = assetManager;
	}
}
