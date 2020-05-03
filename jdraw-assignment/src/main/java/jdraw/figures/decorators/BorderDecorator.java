package jdraw.figures.decorators;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import jdraw.framework.Figure;

public class BorderDecorator extends AbstractFigureDecorator {
	private static final long serialVersionUID = 1L;

	public BorderDecorator(Figure f) {
		super(f);
	}

	@Override
	public Figure clone() {
		return new BorderDecorator(getInner().clone());
	}

	@Override
	public void draw(Graphics g) {
		Rectangle r = getBounds();
		super.draw(g);
		g.setColor(Color.WHITE);
		g.drawLine(r.x, r.y, r.x + r.width, r.y);
		g.drawLine(r.x, r.y, r.x, r.y + r.height);
		g.setColor(Color.DARK_GRAY);
		g.drawLine(r.x, r.y + r.height, r.x + r.width, r.y + r.height);
		g.drawLine(r.x + r.width, r.y, r.x + r.width, r.y + r.height);
	}

	@Override
	public Rectangle getBounds() {
		// grow bounds so the border decorator can be added multiple times
		Rectangle bounds = new Rectangle(getInner().getBounds());
		bounds.grow(1, 1);
		return bounds;
	}
}
