/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures.oval;

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
public class OvalTool extends AbstractDrawTool implements DrawTool {

	/**
	 * Create a new rectangle tool for the given context.
	 * 
	 * @param context a context to use this tool in.
	 */
	public OvalTool(DrawContext context) {
		super(context);
	}

	@Override
	public String getName() {
		return "Oval";
	}

	@Override
	public String getConfiguredIconName() {
		return "oval.png";
	}

	@Override
	public String getConfiguredModeText() {
		return "Oval Mode";
	}

	@Override
	public Figure createFigure(int x, int y, int width, int height) {
		return new Oval(x, y, width, height);
	}

}
