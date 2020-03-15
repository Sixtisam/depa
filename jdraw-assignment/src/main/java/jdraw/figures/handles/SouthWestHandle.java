package jdraw.figures.handles;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

public class SouthWestHandle extends AbstractFigureHandle {

	public SouthWestHandle(Figure fig) {
		super(fig);
	}

	@Override
	public Point getLocation() {
		Point p = getOwner().getBounds().getLocation();
		p.translate(0, (int) getOwner().getBounds().getHeight());
		return p;
	}

	@Override
	protected int getConfiguredPredefinedCursor() {
		return Cursor.SW_RESIZE_CURSOR;
	}

	private Point nwPoint;
	private Point sePoint;

	public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
		Rectangle r = owner.getBounds();
		nwPoint = r.getLocation();

		sePoint = new Point(nwPoint);
		sePoint.translate((int) r.getWidth(), (int) r.getHeight());
//		sePoint = new Point(nwPoint);
//		sePoint.translate((int) r.getWidth(), (int) r.getHeight());
	}

	public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
		nwPoint.translate((int) (x - nwPoint.getX()), 0);
		sePoint.translate(0, (int) (y - sePoint.getY()));
		owner.setBounds(nwPoint, sePoint);
	}

	public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
		sePoint = null;
		nwPoint = null;
	}
}
