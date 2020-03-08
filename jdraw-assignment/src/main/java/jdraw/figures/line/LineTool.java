/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures.line;

import jdraw.figures.AbstractDrawTool;
import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.Figure;

/**
 * This tool defines a mode for drawing ovals.
 *
 * @see jdraw.framework.Figure
 *
 */
public class LineTool extends AbstractDrawTool implements DrawTool {

	/**
	 * Create a new rectangle tool for the given context.
	 * 
	 * @param context a context to use this tool in.
	 */
	public LineTool(DrawContext context) {
		super(context);
	}

	@Override
	public String getName() {
		return "Line";
	}

	@Override
	public String getConfiguredIconName() {
		return "line.png";
	}

	@Override
	public String getConfiguredModeText() {
		return "Line Mode";
	}

	@Override
	public Figure createFigure(int x, int y, int width, int height) {
		return new Line(x, y, width, height);
	}

}
