/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures.hexagon;

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
public class HexagonTool extends AbstractDrawTool implements DrawTool {

	/**
	 * Create a new rectangle tool for the given context.
	 * 
	 * @param context a context to use this tool in.
	 */
	public HexagonTool(DrawContext context) {
		super(context);
	}

	@Override
	public String getName() {
		return "Hexagon";
	}

	@Override
	public String getConfiguredIconName() {
		return "hexagon.png";
	}

	@Override
	public String getConfiguredModeText() {
		return "Hexagon Mode";
	}

	@Override
	public Figure createFigure(int x, int y, int width, int height) {
		return new Hexagon(x, y, Math.max(height, width));
	}

}
