package jdraw.figures.handles;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

public class SouthEastHandle extends AbstractFigureHandle {
	public SouthEastHandle(Figure fig) {
		super(fig);
	}

	@Override
	public Point getLocation() {
		Point p = getOwner().getBounds().getLocation();
		p.translate((int) getOwner().getBounds().getWidth(), (int) getOwner().getBounds().getHeight());
		return p;
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
		owner.setBounds(nwPoint, new Point(x, y));
	}

	@Override
	public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
		sePoint = null;
		nwPoint = null;

	}

	@Override
	protected int getConfiguredPredefinedCursor() {
		return Cursor.SE_RESIZE_CURSOR;
	}

}
