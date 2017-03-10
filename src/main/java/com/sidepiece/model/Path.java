package com.sidepiece.model;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.util.CollectionUtils;

public class Path {
	
	/*
	 * The output data we care about from this object
	 */
	private Double zVelMax;
	private Double zAccMax;
	private Double zPowMax;
	// TODO: Usefulness of two maximums for two-phase lifts (n-phase lifts?)
	
	SortedMap<Double, Point> timeToPosition = new TreeMap<>();
	SortedMap<Double, Point> timeToVelocity = new TreeMap<>();
	SortedMap<Double, Point> timeToAcceleration = new TreeMap<>();
	
	public double getMaxVelocity_iterative(Comparator<Point> comparator) {
		if (zVelMax != null)
			return zVelMax;
		
		return 0;
	}
	
	private void generateTimeToVelocity() {
		
		Double prevTime = null;
		Point prevPos = null;
		
		// Should give keys in sorted order
		for (Double time : timeToPosition.keySet()) {
			Point currentPos = timeToPosition.get(time);
			if (prevTime == null)
				timeToVelocity.put(time, new Point(0d, 0d, 0d));
			else {
				Double dT = time - prevTime;
				timeToVelocity.put(time, Point.makeDerivativePoint(prevPos, currentPos, dT));
			}
			prevTime = time;
			prevPos = currentPos;
		}
	}

}
