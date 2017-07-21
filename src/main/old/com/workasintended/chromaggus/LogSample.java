package com.workasintended.chromaggus;

import java.util.HashMap;

public class LogSample {
	private static HashMap<String, Long> counter = new HashMap<String, Long>();
	public static void queue(String id, String text, long next) {		
		long now = System.currentTimeMillis();
		if(now < next) return;
	}
}
