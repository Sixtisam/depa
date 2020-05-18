/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.commands;

import java.util.List;
import java.util.stream.Collectors;

import jdraw.framework.DrawCommand;
import jdraw.framework.DrawModel;
import jdraw.framework.Figure;

/**
 * Removes a figure from the drawing model. This removal can be undone.
 * 
 * @author Christoph Denzler
 * 
 */
public class AddFigureCommand implements DrawCommand {
	private static final long serialVersionUID = 9121230304586234374L;

	/** The model from which to remove the figure. */
	private final DrawModel model;
	/** The figure to remove. */
	private final Figure figure;


	public AddFigureCommand(DrawModel model, Figure figure) {
		this.model = model;
		this.figure = figure;
	}

	/**
	 * Remove the figure from the model. Does nothing if the figure was already
	 * removed.
	 */
	@Override
	public void redo() {
		model.addFigure(figure);
	}

	@Override
	public void undo() {
		model.removeFigure(figure);
	}

}
