package jdraw.figures.rect;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.RectangularShape;

import jdraw.figures.AbstractFigure;

public abstract class AbstractRectangularFigure extends AbstractFigure {
	private static final long serialVersionUID = 1L;

	protected abstract RectangularShape getShape();

	@Override
	public void setBounds(Point origin, Point corner) {
		getShape().setFrameFromDiagonal(origin, corner);
		notifyListeners();
	}

	@Override
	public boolean contains(int x, int y) {
		return getShape().contains(x, y);
	}

	@Override
	public Rectangle getBounds() {
		return getShape().getBounds();
	}

	@Override
	public void move(int dx, int dy) {
		RectangularShape shape = getShape();
		getShape().setFrame(shape.getX() + dx, shape.getY() + dy, shape.getWidth(), shape.getHeight());
		if (dx != 0 && dy != 0) {
			notifyListeners();
		}
	}

}