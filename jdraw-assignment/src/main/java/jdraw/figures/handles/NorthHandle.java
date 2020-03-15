package jdraw.figures.handles;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

public class NorthHandle extends AbstractFigureHandle {

	public NorthHandle(Figure fig) {
		super(fig);
	}

	@Override
	public Point getLocation() {
		Point p = getOwner().getBounds().getLocation();
		p.translate((int) (getOwner().getBounds().getWidth() / 2), 0);
		return p;
	}

	@Override
	protected int getConfiguredPredefinedCursor() {
		return Cursor.N_RESIZE_CURSOR;
	}

	private Point sePoint;

	public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
		sePoint = new Point(getOwner().getBounds().getLocation());
		sePoint.translate((int) getOwner().getBounds().getWidth(), (int) getOwner().getBounds().getHeight());
	}

	public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
		Point nwPoint = getOwner().getBounds().getLocation();
		nwPoint.setLocation(nwPoint.getX(), y);
		owner.setBounds(nwPoint, sePoint);
	}

	public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
		sePoint = null;
	}
}
