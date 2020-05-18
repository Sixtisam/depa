package logo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import logo.commands.turtle.TurtleCommand;

public class StdHistoryManager implements HistoryManager {
	private final LogoInterpreter interpreter;

	private List<TurtleCommand> commands = new ArrayList<>();
	/**
	 * the index of the newest command, which is executed
	 */
	private int currentIndex = -1;

	public StdHistoryManager(LogoInterpreter interpreter) {
		this.interpreter = interpreter;
	}

	/**
	 * Adds a command to the history. The invoker of the add method is responsible
	 * to execute the command, method add only registers the command in the history
	 * for a subsequent undo/redo call.
	 */
	@Override
	public void addCommand(TurtleCommand command) {
		// discard any changes which are beyond currentIndex
		if(currentIndex + 1 != commands.size()) {
			commands = commands.subList(0, currentIndex + 1);			
		}
		commands.add(command);
		currentIndex += 1;
	}

	/**
	 * Clears the history.
	 */
	@Override
	public void clear() {
		interpreter.resetTurtle();
		currentIndex = -1;
	}

	/**
	 * Performs an undo operation with the effect, that method getCommand returns a
	 * list which does not contain the latest operation. If an undo is not possible
	 * as the list of commands is empty, a message should be printed on the console.
	 * Method undo is also responsible to actualize the window by invoking
	 * interpreter.repaint().
	 */
	@Override
	public void undo() {
		if (currentIndex != -1) {
			currentIndex -= 1;
			redraw();
		}
	}

	/**
	 * Restores the least recently undone operation. If a redo is not possible as no
	 * pending undone commands are available, a message should be printed on the
	 * console. Method redo is also responsible to actualize the window by executing
	 * the command to redo.
	 */
	@Override
	public void redo() {
		if (currentIndex + 1 < commands.size()) {
			currentIndex += 1;
			redraw();
		}
	}

	private void redraw() {
		interpreter.resetTurtle();
		for (int i = 0; i <= currentIndex; i++) {
			commands.get(i).execute();
		}
	}

	/**
	 * Returns all commands which have been registered. This method is used by
	 * method LogoInterpreter.repaint().
	 */
	@Override
	public Iterable<TurtleCommand> getCommands() {
		if (currentIndex == -1) {
			return Collections.emptyList();
		} else {
			return commands.subList(0, currentIndex);
		}
	}
}
