package jdraw.test;

import jdraw.figures.AbstractFigure;
import jdraw.figures.decorators.BorderDecorator;
import jdraw.figures.rect.Rect;

public class BorderDecoratorTest extends AbstractFigureTest {

	@Override
	public AbstractFigure createFigure() {
		return new BorderDecorator(new Rect(1, 1, 20, 10));
	}
}
