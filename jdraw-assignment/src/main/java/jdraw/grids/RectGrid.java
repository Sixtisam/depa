package jdraw.grids;

import java.awt.Point;

import jdraw.framework.DrawGrid;

/**
 * A grid which allows to position elements only every 20 pixels
 *
 */
public class RectGrid implements DrawGrid {

	@Override
	public Point constrainPoint(Point p) {
		return new Point(20 * ((int) p.getX() / 20), 20 * ((int) p.getY() / 20));
	}

	@Override
	public int getStepX(boolean right) {
		return 20;
	}

	@Override
	public int getStepY(boolean down) {
		return 20;
	}

	@Override
	public void activate() {

	}

	@Override
	public void deactivate() {

	}

	@Override
	public void mouseDown() {
	}

	@Override
	public void mouseUp() {
	}

}
