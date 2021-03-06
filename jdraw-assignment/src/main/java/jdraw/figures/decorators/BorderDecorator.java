package jdraw.figures.decorators;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import jdraw.framework.Figure;

public class BorderDecorator extends AbstractFigureDecorator {
	private static final long serialVersionUID = 1L;

	public BorderDecorator(Figure f) {
		super(f);
	}

	@Override
	public Figure clone() {
		return new BorderDecorator(getInner() == null ? null : getInner().clone());
	}

	@Override
	public void drawImpl(Graphics g) {
		Rectangle r = getBounds();
		super.drawImpl(g);
		g.setColor(Color.WHITE);
		g.drawLine(r.x, r.y, r.x + r.width, r.y);
		g.drawLine(r.x, r.y, r.x, r.y + r.height);
		g.setColor(Color.DARK_GRAY);
		g.drawLine(r.x, r.y + r.height, r.x + r.width, r.y + r.height);
		g.drawLine(r.x + r.width, r.y, r.x + r.width, r.y + r.height);
	}

	@Override
	public void setBoundsImpl(Point origin, Point corner) {
		Rectangle rec = new Rectangle(origin);
		rec.setFrameFromDiagonal(origin, corner);
		rec.grow(-1, -1);
		Point rightBottom = rec.getLocation();
		rightBottom.translate((int) rec.getWidth(), (int) rec.getHeight());
		getInner().setBoundsImpl(rec.getLocation(), rightBottom);
	}

	@Override
	public Rectangle getBoundsImpl() {
		// grow bounds so the border decorator can be added multiple times
		Rectangle bounds = new Rectangle(getInner().getBoundsImpl());
		bounds.grow(1, 1);
		return bounds;
	}
}
