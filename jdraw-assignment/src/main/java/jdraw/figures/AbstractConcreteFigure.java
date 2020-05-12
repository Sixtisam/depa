package jdraw.figures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

public abstract class AbstractConcreteFigure extends AbstractFigure {
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
		FigureEvent event = createFigureEvent();
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
	public List<FigureHandle> getHandlesImpl() {
		return Collections.emptyList();
	}

	@Override
	public Figure clone() {
		AbstractConcreteFigure fig = (AbstractConcreteFigure) super.clone();
		// explicitly do not copy the listeners: it is not expected that a listener
		// originally attached to a figure is later
		// attached to the copied figure.
		fig.m_listeners = new ArrayList<>();
		return fig;
	}
}
