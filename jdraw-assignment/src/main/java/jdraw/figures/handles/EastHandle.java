package jdraw.figures.handles;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

public class EastHandle extends AbstractFigureHandle {

	public EastHandle(Figure fig) {
		super(fig);
	}

	@Override
	public Point getLocation() {
		Point p = getOwner().getBounds().getLocation();
		p.translate((int) getOwner().getBounds().getWidth(), (int) getOwner().getBounds().getHeight() / 2);
		return p;
	}

	@Override
	protected int getConfiguredPredefinedCursor() {
		return Cursor.E_RESIZE_CURSOR;
	}

	private Point nwPoint;
	private Point sePoint;

	public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
		nwPoint = new Point(getOwner().getBounds().getLocation());
		sePoint = new Point(nwPoint);
		sePoint.translate((int) getOwner().getBounds().getWidth(), (int) getOwner().getBounds().getHeight());
	}

	public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
		sePoint.setLocation(x, sePoint.getY());
		owner.setBounds(nwPoint, sePoint);
	}

	public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
	}
}
