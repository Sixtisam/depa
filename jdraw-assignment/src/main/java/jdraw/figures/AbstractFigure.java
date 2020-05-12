package jdraw.figures;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;

public abstract class AbstractFigure implements Figure {
	private static final long serialVersionUID = 1L;

	private Figure outer;

	public void setOuter(Figure outer) {
		this.outer = outer;
	}

	public Figure getOuter() {
		return outer;
	}

	protected Figure getOutermost() {
		return outer == null ? this : outer;
	}

	public boolean isSame(Figure other) {
		return isSameImpl(other) || (other != null && other.isSameImpl(this));
	}

	public boolean isSameImpl(Figure other) {
		return this == other;
	}

	@Override
	public final FigureEvent createFigureEvent() {
		if (outer == null) {
			return new FigureEvent(this);
		} else {
			return outer.createFigureEvent();
		}
	}

	@Override
	public boolean isOfType(Class<?> type) {
		return type.isAssignableFrom(this.getClass());
	}

	@Override
	public <V> V getOfType(Class<V> type) {
		return type.cast(this); // checked version of (T)this, accepted by compiler
	}

	@Override
	public final void move(int dx, int dy) {
		if (outer == null) {
			moveImpl(dx, dy);
		} else {
			outer.move(dx, dy);
		}
	}

	@Override
	public final void draw(Graphics g) {
		if (outer == null) {
			drawImpl(g);
		} else {
			outer.draw(g);
		}
	}

	@Override
	public final Rectangle getBounds() {
		if (outer == null) {
			return getBoundsImpl();
		} else {
			return outer.getBounds();
		}
	}

	@Override
	public final List<FigureHandle> getHandles() {
		if (outer == null) {
			return getHandlesImpl();
		} else {
			return outer.getHandles();
		}
	}

	@Override
	public final boolean contains(int x, int y) {
		if (outer == null) {
			return containsImpl(x, y);
		} else {
			return outer.contains(x, y);
		}
	}

	@Override
	public final void setBounds(Point origin, Point corner) {
		if (outer == null) {
			setBoundsImpl(origin, corner);
		} else {
			outer.setBounds(origin, corner);
		}

	}

	@Override
	public Figure clone() {
		try {
			return (Figure) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}