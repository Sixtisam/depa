/*
 * Copyright (c) 2000-2014 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.grids;

import java.awt.Point;
import java.awt.Rectangle;

import jdraw.framework.DrawContext;
import jdraw.framework.Figure;
import jdraw.framework.DrawGrid;

/**
 * A imaginary grid that consists of all lines created by extending the bounding
 * boxes to infinity.
 */
public class BoundingBoxGrid implements DrawGrid {
	/** The context that contains the view with this grid. */
	private DrawContext context;

	/**
	 * A new bounding box grid for the given controller.
	 * @param dc controller.
	 */
	public BoundingBoxGrid(DrawContext dc) {
		context = dc;
	}

	@Override
	public Point constrainPoint(Point p) {
		Point d = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
		Point g = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);

		context.getView().getModel().getFigures().forEachOrdered(f -> {
			Rectangle b = f.getBounds();
			// compute absolut distance of p to b.x, b.y
			int tx = b.x - p.x;
			tx = (tx < 0) ? -tx : tx;
			int ty = b.y - p.y;
			ty = (ty < 0) ? -ty : ty;
			
			// if horizontal distance closer than to any other b.x
			// i.e. any other vertical line
			if (tx < d.x) {
				// remember this distance and reference point b.x
				d.x = tx;
				g.x = b.x;
			}
			// if vertical distance closer than to any other b.y
			// (i.e. any other horizontal line)
			if (ty < d.y) {
				// remember this distance and reference point b.y
				d.y = ty;
				g.y = b.y;
			}
			
			// compute absolut distance of p to b.x + b.w, b.y + b.h
			tx = (b.x + b.width) - p.x;
			tx = (tx < 0) ? -tx : tx;
			ty = (b.y + b.height) - p.y;
			ty = (ty < 0) ? -ty : ty;
			
			// if horizontal distance closer than to any other
			// vertical line
			if (tx < d.x) {
				d.x = tx;
				g.x = b.x + b.width;
			}
			// if vertical distance closer than to any other
			// horizontal line
			if (ty < d.y) {
				d.y = ty;
				g.y = b.y + b.height;
			}
			
			// do same to central lines to get more grid lines
			tx = getCenter(f).x - p.x;
			tx = (tx < 0) ? -tx : tx;
			ty = getCenter(f).y - p.y;
			ty = (ty < 0) ? -ty : ty;
			if (tx < d.x) {
				d.x = tx;
				g.x = getCenter(f).x;
			}
			if (ty < d.y) {
				d.y = ty;
				g.y = getCenter(f).y;
			}
		});
		if (g.x == Integer.MAX_VALUE || g.y == Integer.MAX_VALUE) {
			return null;
		} else {
			return g;
		}
	}

	/**
	 * Not possible, as the position of the Figure to move is not known.
	 * @param right ignored
	 * @return always 0
	 */
	@Override
	public int getStepX(boolean right) {
		return 0;
	}

	/**
	 * Not possible, as the position of the Figure to move is not known.
	 * @param down ignored
	 * @return always 0
	 */
	@Override
	public int getStepY(boolean down) {
		return 0;
	}

	/**
	 * Helper method to determine the center point of a figure.
	 * @param f the figure we are interested in.
	 * @return the center point of given figure.
	 */
	private static Point getCenter(Figure f) {
		java.awt.Rectangle r = f.getBounds();
		return new Point(r.x + r.width / 2, r.y + r.height / 2);
	}

	@Override
	public void activate() {
	}

	@Override
	public void deactivate() {
	}

	@Override
	public void mouseDown() {
	}

	@Override
	public void mouseUp() {
	}

}