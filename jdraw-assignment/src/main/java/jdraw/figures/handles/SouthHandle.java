package jdraw.figures.handles;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

public class SouthHandle extends AbstractFigureHandle {

	public SouthHandle(Figure fig) {
		super(fig);
	}

	@Override
	public Point getLocation() {
		Point p = getOwner().getBounds().getLocation();
		p.translate((int) (getOwner().getBounds().getWidth() / 2), (int) getOwner().getBounds().getHeight());
		return p;
	}

	@Override
	protected int getConfiguredPredefinedCursor() {
		return Cursor.S_RESIZE_CURSOR;
	}

	private Point nwPoint;
	private Point sePoint;

	public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
		nwPoint = new Point(getOwner().getBounds().getLocation());
		sePoint = new Point(nwPoint);
		sePoint.translate((int) getOwner().getBounds().getWidth(), (int) getOwner().getBounds().getHeight());
	}

	public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
		sePoint.setLocation(sePoint.getX(), y);
		owner.setBounds(nwPoint, sePoint);
	}

	public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
	}
}
