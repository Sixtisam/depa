package jdraw.figures;

import java.util.ArrayList;
import java.util.List;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

public abstract class AbstractFigure implements Figure {
	private static final long serialVersionUID = 1L;

	private ArrayList<FigureListener> m_listeners = new ArrayList<>();

	@Override
	public void addFigureListener(FigureListener listener) {
		if (listener != null)
			m_listeners.add(listener);
	}

	@Override
	public void removeFigureListener(FigureListener listener) {
		if (listener != null)
			m_listeners.remove(listener);
	}

	protected void notifyListeners() {
		FigureEvent event = new FigureEvent(this);
		// we have to use a regular for loop, because else a
		// ConcurrentModificationException can occur
		FigureListener[] listeners = m_listeners.toArray(new FigureListener[0]);
		for (FigureListener l : listeners) {
			l.figureChanged(event);
		}
	}

	/**
	 * Returns a list of 8 handles for this Rectangle.
	 * 
	 * @return all handles that are attached to the targeted figure.
	 * @see jdraw.framework.Figure#getHandles()
	 */
	@Override
	public List<FigureHandle> getHandles() {
		return null;
	}

	@Override
	public Figure clone() {
		try {
			AbstractFigure fig = (AbstractFigure) super.clone();
			fig.m_listeners = new ArrayList<>();
			return fig;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}