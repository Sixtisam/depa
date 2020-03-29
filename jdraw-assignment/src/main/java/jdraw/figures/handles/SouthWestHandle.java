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

	private Point anchor;
	private double proportion;

	public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
		Rectangle r = owner.getBounds();
		Point loc = r.getLocation();

		anchor = new Point(loc.x + r.width, loc.y);
		proportion = (double) r.width / (double) r.height;
	}

	public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
		if(e.isShiftDown()) {
			int nh = anchor.y - y;
			int nw = (int) proportion * nh;
			owner.setBounds(anchor, new Point(nw + anchor.x, y));
		} else {
			owner.setBounds(anchor, new Point(x,y));
		}
	}

	public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
		anchor = null;
	}
}
