package jdraw.figures.decorators;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

import jdraw.figures.AbstractFigure;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

public abstract class AbstractFigureDecorator extends AbstractFigure {
	private static final long serialVersionUID = 1L;

	private Figure inner;

	public AbstractFigureDecorator(Figure inner) {
		super();
		if (inner != null) {
			inner.setOuter(this);
		}
		this.inner = inner;
	}

	@Override
	public final boolean isSameImpl(Figure other) {
		return this == other || inner.isSameImpl(other);
	}

	@Override
	public final <T> T getOfType(Class<T> type) {
		if (type.isAssignableFrom(this.getClass())) {
			return type.cast(this); // checked version of (T)this
		} else {
			return inner.getOfType(type);
		}
	}

	@Override
	public final boolean isOfType(Class<?> type) {
		return type.isAssignableFrom(this.getClass()) || inner.isOfType(type);
	}

	public Figure getInner() {
		return inner;
	}

	public void setInner(Figure inner) {
		this.inner = inner;
	}

	public void drawImpl(Graphics g) {
		inner.drawImpl(g);
	}

	public void moveImpl(int dx, int dy) {
		inner.moveImpl(dx, dy);
	}

	public boolean containsImpl(int x, int y) {
		return inner.containsImpl(x, y);
	}

	public void setBoundsImpl(Point origin, Point corner) {
		inner.setBoundsImpl(origin, corner);
	}

	public Rectangle getBoundsImpl() {
		return inner.getBoundsImpl();
	}

	public List<FigureHandle> getHandlesImpl() {
		// decorate all handles so handle.getOwner() will point to decorator instead of
		// decorated object
		return inner.getHandlesImpl().stream().map(h -> new HandleDecorator(this, h)).collect(Collectors.toList());
	}

	public void addFigureListener(FigureListener listener) {
		inner.addFigureListener(listener);
	}

	public void removeFigureListener(FigureListener listener) {
		inner.removeFigureListener(listener);
	}

	// clone must be implemented by each subclass
	public abstract Figure clone();

	public static class HandleDecorator implements FigureHandle {
		private Figure decorator;
		private FigureHandle wrapped;

		public HandleDecorator(Figure owner, FigureHandle wrapped) {
			this.decorator = owner;
			this.wrapped = wrapped;
		}

		@Override
		public Figure getOwner() {
			// hide decorator of decorated object
			return decorator;
		}

		public Point getLocation() {
			return wrapped.getLocation();
		}

		public void draw(Graphics g) {
			wrapped.draw(g);
		}

		public Cursor getCursor() {
			return wrapped.getCursor();
		}

		public boolean contains(int x, int y) {
			return wrapped.contains(x, y);
		}

		public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
			wrapped.startInteraction(x, y, e, v);
		}

		public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
			wrapped.dragInteraction(x, y, e, v);
		}

		public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
			wrapped.stopInteraction(x, y, e, v);
		}

	}
}
