package jdraw.figures.handles;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import jdraw.figures.line.Line;
import jdraw.framework.DrawView;

public class Point1Handle extends AbstractFigureHandle {

	public Point1Handle(Line figure) {
		super(figure);
	}

	@Override
	public Line getOwner() {
		return (Line) super.getOwner();
	}

	@Override
	protected int getConfiguredPredefinedCursor() {
		return Cursor.MOVE_CURSOR;
	}

	@Override
	public Point getLocation() {
		return new Point((int) getOwner().getLine().getX1(), (int) getOwner().getLine().getY1());
	}

	private Point other;

	@Override
	public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
		other = new Point((int) getOwner().getLine().getX2(), (int) getOwner().getLine().getY2());
	}

	@Override
	public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
		getOwner().setBounds(new Point(x, y), other);
	}

	@Override
	public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
		other = null;
	}

}
