package jdraw.figures.handles;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

public class WestHandle extends AbstractFigureHandle {

	public WestHandle(Figure fig) {
		super(fig);
	}

	@Override
	public Point getLocation() {
		Point p = getOwner().getBounds().getLocation();
		p.translate(0, (int) getOwner().getBounds().getHeight() / 2);
		return p;
	}

	@Override
	protected int getConfiguredPredefinedCursor() {
		return Cursor.W_RESIZE_CURSOR;
	}

	private Point nwPoint;
	private Point sePoint;

	public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
		nwPoint = new Point(getOwner().getBounds().getLocation());
		sePoint = new Point(nwPoint);
		sePoint.translate((int) getOwner().getBounds().getWidth(), (int) getOwner().getBounds().getHeight());
	}

	public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
		nwPoint.setLocation(x, nwPoint.getY());
		owner.setBounds(nwPoint, sePoint);
	}

	public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
	}
}
