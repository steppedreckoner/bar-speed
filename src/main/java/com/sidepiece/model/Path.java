package com.sidepiece.model;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

public class Path {
	
	/*
	 * The output data we care about from this object
	 */
	private Double zVelMax;
	private Double zAccMax;
	private Double zPowMax;
	// TODO: Usefulness of two maximums for two-phase lifts (n-phase lifts?)
	
	// Max value is 32767 -- plenty
	private Short mass;
	
	SortedMap<Double, Point> timeToPosition = new TreeMap<>();
	SortedMap<Double, Point> timeToVelocity = new TreeMap<>();
	SortedMap<Double, Point> timeToAcceleration = new TreeMap<>();
	
	public Double getZVelMax() {
		if (zVelMax == null)
			zVelMax = getMaxVelocityPoint_iterative(Point.zComp).getZVal();
		return zVelMax;
	}
	
	public Double getZMaxAcc() {
		if (zAccMax == null)
			zAccMax = getMaxAccelerationPoint_iterative(Point.zComp).getZVal();
		return zAccMax;
	}
	
	public Double getZPowMax() {
		if (zPowMax == null)
			zPowMax = getMaxAccelerationPoint_iterative(Point.zComp).getZVal() * mass;
		return zPowMax;
	}
	
	private Point getMaxVelocityPoint_iterative(Comparator<Point> comparator) {
		if (timeToVelocity == null)
			generateTimeToVelocity_iterative();
		
		Point max = null;
		for (Point p : timeToVelocity.values()) {
			if (max == null || comparator.compare(p, max) == 1)
				max = p;
		}
		return max;
	}
	
	private void generateTimeToVelocity_iterative() {
		
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
	
	private Point getMaxAccelerationPoint_iterative(Comparator<Point> comparator) {
		if (timeToVelocity == null)
			generateTimeToVelocity_iterative();
		if (timeToAcceleration == null)
			generateTimeToAcceleration_iterative();
		
		Point max = null;
		for (Point p : timeToVelocity.values()) {
			if (max == null || comparator.compare(p, max) == 1)
				max = p;
		}
		return max;
	}
	
	private void generateTimeToAcceleration_iterative() {
		Double prevTime = null;
		Point prevVel = null;
		
		// Should give keys in sorted order
		for (Double time : timeToVelocity.keySet()) {
			Point currentVel = timeToVelocity.get(time);
			if (prevTime == null)
				timeToAcceleration.put(time, new Point(0d, 0d, 0d));
			else {
				Double dT = time - prevTime;
				timeToAcceleration.put(time, Point.makeDerivativePoint(prevVel, currentVel, dT));
			}
			prevTime = time;
			prevVel = currentVel;
		}
	}

}
