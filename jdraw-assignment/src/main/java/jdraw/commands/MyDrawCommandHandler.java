package jdraw.commands;

import java.util.Stack;

import jdraw.framework.DrawCommand;
import jdraw.framework.DrawCommandHandler;

public class MyDrawCommandHandler implements DrawCommandHandler {

	private Stack<DrawCommand> undoStack = new Stack<>();
	private Stack<DrawCommand> redoStack = new Stack<>();

	private boolean ignoreAddCommands = false;

	private CompositeDrawCommand currentScriptCommand = null;

	@Override
	public void addCommand(DrawCommand cmd) {
		// ignore any commands created while a command is undoed/redoed
		if (ignoreAddCommands) {
			return;
		}
		if (currentScriptCommand != null) {
			currentScriptCommand.add(cmd);
		} else {
			addCommandImpl(cmd);
		}
	}
	
	protected void addCommandImpl(DrawCommand cmd) {
		redoStack.clear();
		undoStack.add(cmd);
	}

	@Override
	public void undo() {
		if (!undoStack.isEmpty()) {
			try {
				ignoreAddCommands = true;
				DrawCommand cmd = undoStack.pop();
				System.out.println("Undo: " + undoStack.size() + " | Redo: " + redoStack.size());
				cmd.undo();
				redoStack.push(cmd);
				System.out.println("Undo: " + undoStack.size() + " | Redo: " + redoStack.size());
			} finally {
				ignoreAddCommands = false;
			}
		}
	}

	@Override
	public void redo() {
		if (!redoStack.isEmpty()) {
			try {
				ignoreAddCommands = true;
				DrawCommand cmd = redoStack.pop();
				System.out.println("Undo: " + undoStack.size() + " | Redo: " + redoStack.size());
				cmd.redo();
				undoStack.push(cmd);
				System.out.println("Undo: " + undoStack.size() + " | Redo: " + redoStack.size());
			} finally {
				ignoreAddCommands = false;
			}
		}
	}

	@Override
	public boolean undoPossible() {
		return !undoStack.isEmpty();
	}

	@Override
	public boolean redoPossible() {
		return !redoStack.isEmpty();
	}

	@Override
	public void beginScript() {
		System.out.println("beginScript");
		if (currentScriptCommand != null)
			throw new RuntimeException("already in a script");
		currentScriptCommand = new CompositeDrawCommand();
	}

	@Override
	public void endScript() {
		System.out.println("End script");
		if (currentScriptCommand == null)
			throw new RuntimeException("not in a script");
		if(!currentScriptCommand.isEmpty()) {
			addCommandImpl(currentScriptCommand);
		}
		currentScriptCommand = null;
	}

	@Override
	public void clearHistory() {
		redoStack.clear();
		undoStack.clear();
	}

}
