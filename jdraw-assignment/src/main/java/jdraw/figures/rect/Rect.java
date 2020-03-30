/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures.rect;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.RectangularShape;

import jdraw.framework.Figure;

/**
 * Represents rectangles in JDraw.
 * 
 *
 */
public class Rect extends AbstractRectangularFigure {
	private static final long serialVersionUID = 9120181044386552132L;

	/**
	 * Use the java.awt.Rectangle in order to save/reuse code.
	 */
	private Rectangle rectangle;

	/**
	 * Create a new rectangle of the given dimension.
	 * 
	 * @param x the x-coordinate of the upper left corner of the rectangle
	 * @param y the y-coordinate of the upper left corner of the rectangle
	 * @param w the rectangle's width
	 * @param h the rectangle's height
	 */
	public Rect(int x, int y, int w, int h) {
		rectangle = new Rectangle(x, y, w, h);
	}

	@Override
	protected RectangularShape getShape() {
		return rectangle;
	}

	/**
	 * Draw the rectangle to the given graphics context.
	 * 
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		g.setColor(Color.BLACK);
		g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	@Override
	public Figure clone() {
		Rect clone = (Rect) super.clone();
		clone.rectangle = new Rectangle(this.rectangle);
		return clone;
	}
}
