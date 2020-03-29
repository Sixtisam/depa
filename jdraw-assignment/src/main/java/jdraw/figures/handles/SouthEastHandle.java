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

	private Point anchor;
	private double proportion;

	public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
		Rectangle r = owner.getBounds();
		Point loc = r.getLocation();

		anchor = new Point(loc);
		proportion = (double) r.width / (double) r.height;
	}

	public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
		if (e.isShiftDown()) {
			int nh = y - anchor.y;
			int nw = (int) proportion * nh;
			owner.setBounds(anchor, new Point(nw + anchor.x, y));
		} else {
			owner.setBounds(anchor, new Point(x, y));
		}
	}

	@Override
	public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
		anchor = null;
	}

	@Override
	protected int getConfiguredPredefinedCursor() {
		return Cursor.SE_RESIZE_CURSOR;
	}

}
