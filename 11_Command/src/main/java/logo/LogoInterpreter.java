package logo;

import java.awt.Color;
import java.util.Scanner;

import logo.commands.Command;
import logo.commands.control.BeginMacroCommand;
import logo.commands.control.EndMacroCommand;
import logo.commands.control.ErrorCommand;
import logo.commands.control.ExitCommand;
import logo.commands.control.NotFoundCommand;
import logo.commands.control.RedoCommand;
import logo.commands.control.UndoCommand;
import logo.commands.turtle.ClearScreenCommand;
import logo.commands.turtle.MoveCommand;
import logo.commands.turtle.PenDownCommand;
import logo.commands.turtle.PenUpCommand;
import logo.commands.turtle.RepeatCommand;
import logo.commands.turtle.RotateCommand;
import logo.commands.turtle.TurtleCommand;
import logo.parser.CommandRegistry;
import logo.parser.Parser;

public class LogoInterpreter {
	private final Turtles turtles;
	private final HistoryManager historyManager;
	private final MacroManager macroManager;

	public HistoryManager getHistoryManager() {
		return historyManager;
	}

	private Parser parser;

	public LogoInterpreter() {
		turtles = new Turtles();
		historyManager = new StdHistoryManager(this);
		macroManager = new StdMacroManager(this);
		System.out.println("Starting interpreter...");
	}

	public static void main(final String[] args) {
		new LogoInterpreter().run();
	}

	private boolean running;

	public void stop() {
		running = false;
	}

	public void repaint() {
		resetTurtle();
		turtles.setColor(Color.BLACK);
		for (Command c : historyManager.getCommands()) {
			c.execute();
		}
	}

	public void setColor(Color c) {
		turtles.setColor(c);
	}

	public void run() {
		turtles.show();
		initializeParser();
		resetTurtle();
		Scanner scanner = new Scanner(System.in);
		running = true;
		while (running) {
			Command command = parser.parse(scanner);
			if (macroManager.isRecordingMacro()) {
				if (command instanceof TurtleCommand) {
					macroManager.addCommand((TurtleCommand)command);
				} else if (command instanceof EndMacroCommand) {
					command.execute();
				} else {
					System.out.println("This command cannot be used in a macro!");
				}
			} else {
				if (command instanceof TurtleCommand) {
					historyManager.addCommand((TurtleCommand)command);
				}
				System.out.println("" + command);
				command.execute();
			}
		}
		scanner.close();
		turtles.quit();
	}

	private void initializeParser() {
		CommandRegistry commandRegistry = new CommandRegistry();
		commandRegistry.registerCommand("backward", scanner -> new MoveCommand(turtles, -scanner.nextInt()));
		commandRegistry.registerCommand("forward", scanner -> new MoveCommand(turtles, scanner.nextInt()));
		commandRegistry.registerCommand("move", scanner -> new MoveCommand(turtles, scanner.nextInt()));
		commandRegistry.registerCommand("left", scanner -> new RotateCommand(turtles, scanner.nextInt()));
		commandRegistry.registerCommand("right", scanner -> new RotateCommand(turtles, -scanner.nextInt()));
		commandRegistry.registerCommand("exit", scanner -> new ExitCommand(this));
		commandRegistry.registerCommand("clearscreen", scanner -> new ClearScreenCommand(this));
		commandRegistry.registerCommand("penup", scanner -> new PenUpCommand(turtles));
		commandRegistry.registerCommand("pendown", scanner -> new PenDownCommand(turtles));
		commandRegistry.registerCommand("repeat", scanner -> {
			int amount = scanner.nextInt();
			Command cmd = parser.parse(scanner);
			if (cmd instanceof TurtleCommand) return new RepeatCommand(amount, (TurtleCommand)cmd);
			else return new ErrorCommand("Turtle Command expected.");
		});
		commandRegistry.registerCommand("undo", scanner -> new UndoCommand(historyManager));
		commandRegistry.registerCommand("redo", scanner -> new RedoCommand(historyManager));
		commandRegistry.registerCommand("macrorecord", scanner -> new BeginMacroCommand(scanner.next(), macroManager));
		commandRegistry.registerCommand("macrosave", scanner -> new EndMacroCommand(macroManager));
		commandRegistry.registerCommand("macrorun", scanner -> {
			try {
				return macroManager.getCommand(scanner.next());
			}
			catch (Exception e) {
				e.printStackTrace();
				// return relatively gracefully
				return new NotFoundCommand("");
			}
		});
		parser = new Parser(commandRegistry);
	}

	public void resetTurtle() {
		turtles.moveTo(200, 200);
		turtles.clear();
		turtles.setDirection(90);
		turtles.down();
	}
}
