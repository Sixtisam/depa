package jdraw.figures.line;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.List;

import jdraw.figures.AbstractConcreteFigure;
import jdraw.figures.handles.Point1Handle;
import jdraw.figures.handles.Point2Handle;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

public class Line extends AbstractConcreteFigure {
	private static final long serialVersionUID = 1L;
	private Line2D line;

	/**
	 * Line is selectable 10px around the line
	 */
	public static final int GRAVITY = 10;

	/**
	 * Create a new polygon of the given dimension.
	 * 
	 * @param x the x-coordinate of the upper left corner of the rectangle
	 * @param y the y-coordinate of the upper left corner of the rectangle
	 * @param w the rectangle's width
	 * @param h the rectangle's height
	 */
	public Line(int x, int y, int w, int h) {
		line = new Line2D.Double(x, y, x + w, y + h);
	}

	@Override
	public void setBoundsImpl(Point origin, Point corner) {
		line.setLine(origin, corner);
		notifyListeners();
	}

	public Line2D getLine() {
		return line;
	}

	@Override
	public boolean containsImpl(int x, int y) {
		return line.ptSegDistSq(x, y) < GRAVITY * GRAVITY;
	}

	@Override
	public Rectangle getBoundsImpl() {
		return line.getBounds();
	}

	/**
	 * Draw the rectangle to the given graphics context.
	 * 
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void drawImpl(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine((int) line.getX1(), (int) line.getY1(), (int) line.getX2(), (int) line.getY2());
	}

	@Override
	public void moveImpl(int dx, int dy) {
		line.setLine(line.getX1() + dx, line.getY1() + dy, line.getX2() + dx, line.getY2() + dy);
		if (dx != 0 && dy != 0) {
			notifyListeners();
		}
	}

	/**
	 * Returns a list of 8 handles for this Rectangle.
	 * 
	 * @return all handles that are attached to the targeted figure.
	 * @see jdraw.framework.Figure#getHandles()
	 */
	@Override
	public List<FigureHandle> getHandlesImpl() {
		return Arrays.asList(new Point1Handle(this), new Point2Handle(this));
	}

	@Override
	public Figure clone() {
		Line clone = (Line) super.clone();
		clone.line = (Line2D) this.line.clone();
		return clone;
	}
}
