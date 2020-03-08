/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.std;

import java.util.ArrayList;
import java.util.stream.Stream;

import jdraw.framework.DrawCommandHandler;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawModelEvent;
import jdraw.framework.DrawModelEvent.Type;
import jdraw.framework.DrawModelListener;
import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureListener;

/**
 * Provide a standard behavior for the drawing model. This class initially does
 * not implement the methods in a proper way. It is part of the course
 * assignments to do so.
 * 
 * @author TODO add your name here
 *
 */
public class StdDrawModel implements DrawModel, FigureListener {
	private ArrayList<Figure> m_figures = new ArrayList<>();
	private ArrayList<DrawModelListener> m_listeners = new ArrayList<>();

	@Override
	public void addFigure(Figure f) {
		if (!m_figures.contains(f)) {
			m_figures.add(f);
			f.addFigureListener(this);
			notifyListeners(new DrawModelEvent(this, f, Type.FIGURE_ADDED));
		}
	}

	@Override
	public Stream<Figure> getFigures() {
		return m_figures.stream(); // Only guarantees, that the application starts -- has to be replaced !!!
	}

	@Override
	public void removeFigure(Figure f) {
		f.removeFigureListener(this);
		if (m_figures.remove(f)) {
			notifyListeners(new DrawModelEvent(this, f, Type.FIGURE_REMOVED));
		}
	}

	@Override
	public void addModelChangeListener(DrawModelListener listener) {
		m_listeners.add(listener);
	}

	@Override
	public void removeModelChangeListener(DrawModelListener listener) {
		m_listeners.remove(listener);
	}

	/** The draw command handler. Initialized here with a dummy implementation. */
	// TODO initialize with your implementation of the undo/redo-assignment.
	private DrawCommandHandler handler = new EmptyDrawCommandHandler();

	/**
	 * Retrieve the draw command handler in use.
	 * 
	 * @return the draw command handler.
	 */
	@Override
	public DrawCommandHandler getDrawCommandHandler() {
		return handler;
	}

	@Override
	public void setFigureIndex(Figure f, int index) {
		int oldIndex = m_figures.indexOf(f);
		if (oldIndex != -1) {
			if (index >= m_figures.size()) {
				throw new IndexOutOfBoundsException();
			}
			m_figures.add(index, f);
			m_figures.remove(oldIndex < index ? oldIndex : oldIndex + 1);
			notifyListeners(new DrawModelEvent(this, f, Type.DRAWING_CHANGED));
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void removeAllFigures() {
		m_figures.forEach(f -> f.removeFigureListener(this));
		m_figures.clear();
		notifyListeners(new DrawModelEvent(this, null, Type.DRAWING_CLEARED));
	}

	private void notifyListeners(DrawModelEvent e) {
		m_listeners.stream().forEach(l -> l.modelChanged(e));
		DrawModelListener[] listeners = m_listeners.toArray(new DrawModelListener[0]);
		for (DrawModelListener l : listeners) {
			l.modelChanged(e);
		}
	}

	@Override
	public void figureChanged(FigureEvent e) {
		notifyListeners(new DrawModelEvent(this, e.getFigure(), Type.FIGURE_CHANGED));
	}

}
