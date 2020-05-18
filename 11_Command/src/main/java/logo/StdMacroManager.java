package logo;

import java.util.HashMap;
import java.util.Map;

import logo.commands.Command;
import logo.commands.turtle.CompositeCommand;
import logo.commands.turtle.TurtleCommand;

public class StdMacroManager implements MacroManager {
	private final LogoInterpreter interpreter;

	private Map<String, CompositeCommand> macros = new HashMap<>();
	
	private CompositeCommand currentRecording = null;
	
	public StdMacroManager(LogoInterpreter interpreter) {
		this.interpreter = interpreter;
	}

	/**
	 * Indicates whether a macro is currently being recorded.
	 */
	@Override
	public boolean isRecordingMacro() {
		return currentRecording != null;
	}

	/**
	 * Adds a command to the current macro. If this method is invoked while no
	 * macro is being recorded, then a NullPointerException should be thrown.
	 */
	@Override
	public void addCommand(TurtleCommand command) {
		// TODO store the command provided by the parameter in the list of commands
		// to be executed by the macro
		currentRecording.add(command);
		
	}

	/**
	 * Starts the recording of a new macro.
	 */
	@Override
	public void startMacro(String name) {
		// TODO generate a new data structure to store a sequence of TurtleCommands
		// as macro under the given name
		currentRecording = new CompositeCommand(name);
	}

	/**
	 * Closes the recording of the current macro and registers the macro under its
	 * name.
	 */
	@Override
	public void endMacro() {
		// TODO store the recorded list of commands for instance in a map
		macros.put(currentRecording.getName(), currentRecording);
		currentRecording = null;
	}

	/**
	 * Returns the macro command with a given name. If no macro can be found with
	 * this name, then a NotFoundCommand is returned.
	 */
	@Override
	public Command getCommand(String name) {
		return macros.get(name);
	}
}
