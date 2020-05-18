package jdraw.commands;

import java.util.ArrayList;
import java.util.List;

import jdraw.figures.group.GroupService;
import jdraw.framework.DrawCommand;
import jdraw.framework.DrawModel;
import jdraw.framework.Figure;
import jdraw.framework.FigureGroup;

public class GroupCommand implements DrawCommand {
	private static final long serialVersionUID = 1L;

	private DrawModel model;
	private FigureGroup group;
	private int index;

	public GroupCommand(DrawModel model, FigureGroup group, int index) {
		this.model = model;
		this.group = group;
		this.index = index;
	}

	@Override
	public void redo() {
		GroupService.group(model, group, index);
	}

	@Override
	public void undo() {
		GroupService.ungroup(model, group);
	}
}
