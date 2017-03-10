package com.sidepiece.model;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;

public class PathTests {
	
	// z = -.1(5t)^3 + 100t^2
	static Function<Double, Point> generatePositions = t -> {
		return new Point((Math.random() - .5)/20, (Math.random() - .5)/20, -.1 * Math.pow(5*t, 3) + 10 * Math.pow(t, 2));
	};
	
	SortedMap<Double, Point> dummyTimeToPosition = generateDummyTimeToPosition();
	
	private static SortedMap<Double, Point> generateDummyTimeToPosition() {
		SortedMap<Double, Point> newMap = new TreeMap<>();
		for (int i = 0; i < 100; i++) {
			Double t = i/100d;
			newMap.put(t, generatePositions.apply(t));
		}
		return newMap;
	}

}
