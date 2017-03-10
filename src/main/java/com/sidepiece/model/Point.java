package com.sidepiece.model;

import lombok.Data;

import java.util.Comparator;

import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Point {
	
	private final Double xVal;
	private final Double yVal;
	private final Double zVal;
	
	public static Comparator<Point> zComp = (p1, p2) -> {
		return p1.getZVal().compareTo(p2.getZVal());
	};
	
	public static Point makeDerivativePoint(Point p1, Point p2, Double dT) {
		Double x = (p2.getXVal() - p1.getXVal()) / dT;
		Double y = (p2.getYVal() - p1.getYVal()) / dT;
		Double z = (p2.getZVal() - p1.getZVal()) / dT;
		return new Point(x, y, z);
	}

}
