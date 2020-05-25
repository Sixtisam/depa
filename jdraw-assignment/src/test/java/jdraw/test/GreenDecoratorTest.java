package jdraw.test;

import jdraw.figures.AbstractFigure;
import jdraw.figures.decorators.GreenDecorator;
import jdraw.figures.rect.Rect;

public class GreenDecoratorTest extends AbstractFigureTest {

	@Override
	public AbstractFigure createFigure() {
		return new GreenDecorator(new Rect(1, 1, 20, 10));
	}
}
