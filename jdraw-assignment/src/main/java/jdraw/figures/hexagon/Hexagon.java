package jdraw.figures.hexagon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import jdraw.figures.AbstractFigure;

public class Hexagon extends AbstractFigure {
	private static final long serialVersionUID = 1L;
	private Polygon polygon;

	/**
	 * Create a new polygon of the given dimension.
	 * 
	 * @param x the x-coordinate of the upper left corner of the rectangle
	 * @param y the y-coordinate of the upper left corner of the rectangle
	 * @param w the rectangle's width
	 * @param h the rectangle's height
	 */
	public Hexagon(int x, int y, int side) {
		polygon = createPolygon(x, y, side);
	}

	private Polygon createPolygon(double centerX, double centerY, double side) {

		double halfSide = side / 2.0;
//		double halfSide = hexagonSide / 2.0;
		double height = Math.sqrt((4 * side * side) - (side * side));
		// the y-coord of the two edges in the middle
//		double middleY = Math.sqrt((hexagonSide * hexagonSide) - (halfSide * halfSide));
		double halfHeight = height / 2.0;

		int[] hexagonY = new int[6];
		int[] hexagonX = new int[6];
		int i = 0;
		hexagonX[i] = (int) -halfSide;
		hexagonY[i++] = (int) -halfHeight;

		hexagonX[i] = (int) +halfSide;
		hexagonY[i++] = (int) -halfHeight;

		hexagonX[i] = (int) +side;
		hexagonY[i++] = 0;

		hexagonX[i] = (int) (halfSide);
		hexagonY[i++] = (int) halfHeight;

		hexagonX[i] = (int) (-halfSide);
		hexagonY[i++] = (int) halfHeight;

		hexagonX[i] = (int) -side;
		hexagonY[i++] = 0;

		Polygon polygon = new Polygon(hexagonX, hexagonY, 6);
		polygon.translate((int) centerX, (int) centerY);
		return polygon;
	}

	@Override
	public void setBounds(Point origin, Point corner) {
		double sideX = Math.abs(origin.getX() - corner.getX());
		double sideY = Math.sqrt(4 * Math.pow(Math.abs(origin.getY() - corner.getY()), 2) / 3);

		polygon = createPolygon(origin.getX(), origin.getY(), Math.max(sideX, sideY));
		notifyListeners();
	}

	@Override
	public boolean contains(int x, int y) {
		return polygon.contains(x, y);
	}

	@Override
	public Rectangle getBounds() {
		return polygon.getBounds();
	}

	/**
	 * Draw the rectangle to the given graphics context.
	 * 
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillPolygon(polygon);
		g.setColor(Color.BLACK);
		g.drawPolygon(polygon);
	}

	@Override
	public void move(int dx, int dy) {
		polygon.translate(dx, dy);
		if (dx != 0 && dy != 0) {
			notifyListeners();
		}
	}
}
