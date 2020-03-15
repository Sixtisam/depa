package jdraw.figures.rect;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.RectangularShape;
import java.util.Arrays;
import java.util.List;

import jdraw.figures.AbstractFigure;
import jdraw.figures.handles.EastHandle;
import jdraw.figures.handles.NorthEastHandle;
import jdraw.figures.handles.NorthHandle;
import jdraw.figures.handles.NorthWestHandle;
import jdraw.figures.handles.SouthEastHandle;
import jdraw.figures.handles.SouthHandle;
import jdraw.figures.handles.SouthWestHandle;
import jdraw.figures.handles.WestHandle;
import jdraw.framework.FigureHandle;

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

	@Override
	public List<FigureHandle> getHandles() {
		return Arrays.asList(new NorthWestHandle(this), new SouthEastHandle(this), new SouthWestHandle(this),
				new NorthEastHandle(this), new NorthHandle(this), new SouthHandle(this), new WestHandle(this),
				new EastHandle(this));
	}
}