package jdraw.figures.oval;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;

import jdraw.figures.rect.AbstractRectangularFigure;

public class Oval extends AbstractRectangularFigure {
	private static final long serialVersionUID = 1L;
	/**
	 * Use the java.awt.Ellipse2D in order to save/reuse code.
	 */
	final Ellipse2D ellipse;

	/**
	 * Create a new rectangle of the given dimension.
	 * 
	 * @param x the x-coordinate of the upper left corner of the rectangle
	 * @param y the y-coordinate of the upper left corner of the rectangle
	 * @param w the rectangle's width
	 * @param h the rectangle's height
	 */
	public Oval(int x, int y, int w, int h) {
		this.ellipse = new Ellipse2D.Double(x, y, w, h);
	}

	@Override
	protected RectangularShape getShape() {
		return ellipse;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval((int) ellipse.getX(), (int) ellipse.getY(), (int) ellipse.getWidth(), (int) ellipse.getHeight());
		g.setColor(Color.BLACK);
		g.drawOval((int) ellipse.getX(), (int) ellipse.getY(), (int) ellipse.getWidth(), (int) ellipse.getHeight());
	}
}
