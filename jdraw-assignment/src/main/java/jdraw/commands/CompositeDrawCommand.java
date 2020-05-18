package jdraw.commands;

import java.util.LinkedList;

import jdraw.framework.DrawCommand;

public class CompositeDrawCommand implements DrawCommand {
	private static final long serialVersionUID = 1L;

	private LinkedList<DrawCommand> commands = new LinkedList<>();

	public CompositeDrawCommand() {

	}

	public boolean isEmpty() {
		return commands.isEmpty(); 
	}

	public void add(DrawCommand command) {
		commands.add(command);
	}

	@Override
	public void redo() {
		commands.forEach(DrawCommand::redo);
	}

	@Override
	public void undo() {
		commands.descendingIterator().forEachRemaining(DrawCommand::undo);
	}
}
