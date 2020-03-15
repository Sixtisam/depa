package jdraw.figures.handles;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

public abstract class AbstractFigureHandle implements FigureHandle {

	protected Figure owner;

	public AbstractFigureHandle(Figure owner) {
		this.owner = owner;
	}

	@Override
	public Figure getOwner() {
		return owner;
	}

	@Override
	public void draw(Graphics g) {
		Point loc = getLocation();
		g.setColor(Color.WHITE);
		g.fillRect(loc.x - 3, loc.y - 3, 6, 6);
		g.setColor(Color.BLACK);
		g.drawRect(loc.x - 3, loc.y - 3, 6, 6);
	}

	@Override
	public Cursor getCursor() {
		return Cursor.getPredefinedCursor(getConfiguredPredefinedCursor());
	}
	
	protected abstract int getConfiguredPredefinedCursor();

	@Override
	public boolean contains(int x, int y) {
		Point loc = getLocation();
		Rectangle rect = new Rectangle((int) loc.getX() - 3, (int) loc.getY() - 3, 6, 6);
		return rect.contains(x, y);
	}

}