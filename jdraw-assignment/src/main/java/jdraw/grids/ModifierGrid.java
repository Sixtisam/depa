package jdraw.grids;

import java.awt.Point;

import jdraw.framework.DrawGrid;

public class ModifierGrid implements DrawGrid {

	@Override
	public Point constrainPoint(Point p) {
		return p;
	}

	@Override
	public int getStepX(boolean right) {
		return 1;
	}

	@Override
	public int getStepY(boolean down) {
		return 1;
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseUp() {
		// TODO Auto-generated method stub

	}

}
