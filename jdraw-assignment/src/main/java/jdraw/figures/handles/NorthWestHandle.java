package jdraw.figures.handles;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

public class NorthWestHandle extends AbstractFigureHandle {

	public NorthWestHandle(Figure fig) {
		super(fig);
	}

	@Override
	public Point getLocation() {
		return getOwner().getBounds().getLocation();
	}

	@Override
	protected int getConfiguredPredefinedCursor() {
		return Cursor.NW_RESIZE_CURSOR;
	}

	private Point nwPoint;
	private Point sePoint;
	private double proportion;

	public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
		Rectangle r = owner.getBounds();
		nwPoint = r.getLocation();

		sePoint = new Point(nwPoint);
		sePoint.translate((int) r.getWidth(), (int) r.getHeight());
		proportion = (double) r.width / (double) r.height;
	}

	public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
		if(e.isShiftDown()) {
			int nh = nwPoint.y - y;
			int nw = (int) proportion * nh;
			owner.setBounds(new Point(nwPoint.x - nw, y), sePoint);
		} else {
			owner.setBounds(new Point(x,y), sePoint);
		}
	}

	public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
		sePoint = null;
		nwPoint = null;
	}
}
