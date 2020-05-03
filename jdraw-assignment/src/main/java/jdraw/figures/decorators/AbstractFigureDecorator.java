package jdraw.figures.decorators;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

public abstract class AbstractFigureDecorator implements Figure {
	private static final long serialVersionUID = 1L;

	private final Figure inner;

	public AbstractFigureDecorator(Figure inner) {
		super();
		this.inner = inner;
	}

	public Figure getInner() {
		return inner;
	}

	public void draw(Graphics g) {
		inner.draw(g);
	}

	public void move(int dx, int dy) {
		inner.move(dx, dy);
	}

	public boolean contains(int x, int y) {
		return inner.contains(x, y);
	}

	public void setBounds(Point origin, Point corner) {
		inner.setBounds(origin, corner);
	}

	public Rectangle getBounds() {
		return inner.getBounds();
	}

	public List<FigureHandle> getHandles() {
		// decorate all handles so handle.getOwner() will point to decorator instead of
		// decorated object
		return inner.getHandles().stream().map(h -> new HandleDecorator(this, h)).collect(Collectors.toList());
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
