package jdraw.figures.handles;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

public class NorthEastHandle extends AbstractFigureHandle {

	public NorthEastHandle(Figure fig) {
		super(fig);
	}

	@Override
	public Point getLocation() {
		Point p = getOwner().getBounds().getLocation();
		p.translate((int) getOwner().getBounds().getWidth(), 0);
		return p;
	}

	@Override
	protected int getConfiguredPredefinedCursor() {
		return Cursor.NE_RESIZE_CURSOR;
	}

	private Point nwPoint;
	private Point sePoint;

	public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
		Rectangle r = owner.getBounds();
		nwPoint = r.getLocation();

		sePoint = new Point(nwPoint);
		sePoint.translate((int) r.getWidth(), (int) r.getHeight());
	}

	public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
		sePoint.translate((int) (x - sePoint.getX()), 0);
		nwPoint.translate(0, (int) (y - nwPoint.getY()));
		owner.setBounds(nwPoint, sePoint);
	}

	public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
		sePoint = null;
		nwPoint = null;
	}
}
