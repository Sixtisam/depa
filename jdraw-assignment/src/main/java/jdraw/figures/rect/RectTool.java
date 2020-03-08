/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures.rect;

import jdraw.figures.AbstractDrawTool;
import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.Figure;

/**
 * This tool defines a mode for drawing rectangles.
 *
 * @see jdraw.framework.Figure
 *
 */
public class RectTool extends AbstractDrawTool implements DrawTool {

	/**
	 * Create a new rectangle tool for the given context.
	 * 
	 * @param context a context to use this tool in.
	 */
	public RectTool(DrawContext context) {
		super(context);
	}

	@Override
	public String getName() {
		return "Rectangle";
	}

	@Override
	public String getConfiguredIconName() {
		return "rectangle.png";
	}

	@Override
	public String getConfiguredModeText() {
		return "Rectangle Mode";
	}

	@Override
	public Figure createFigure(int x, int y, int width, int height) {
		return new Rect(x, y, width, height);
	}

}
