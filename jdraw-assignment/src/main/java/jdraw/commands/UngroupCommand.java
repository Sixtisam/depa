package jdraw.commands;

import jdraw.figures.group.GroupService;
import jdraw.framework.DrawCommand;
import jdraw.framework.DrawModel;
import jdraw.framework.FigureGroup;

public class UngroupCommand implements DrawCommand {
	private static final long serialVersionUID = 1L;

	private DrawModel model;
	private FigureGroup group;
	private int index;

	public UngroupCommand(DrawModel model, FigureGroup group, int index) {
		this.model = model;
		this.group = group;
	}

	@Override
	public void redo() {
		GroupService.ungroup(model, group);
	}

	@Override
	public void undo() {
		GroupService.group(model, group, index);
	}
}
