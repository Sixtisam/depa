package jdraw.test;

import jdraw.figures.AbstractFigure;
import jdraw.figures.rect.Rect;

public class RectangleTest extends AbstractFigureTest {
	@Override
	public AbstractFigure createFigure() {
		return new Rect(1, 1, 20, 10);
	}
}
