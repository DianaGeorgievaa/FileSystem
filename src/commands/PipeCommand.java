package commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import exceptions.ExistanceException;
import exceptions.InvalidArgumentException;
import exceptions.NotEnoughMemoryException;
import filesystem.FileSystem;

public class PipeCommand extends Command {
	private CommandFactory commandFactory;

	public PipeCommand(FileSystem fileSystem) {
		super(fileSystem);
		parser = new Parser();
		commandFactory = new CommandFactory();
	}

	/**
	 * Returns arguments for the command.
	 * 
	 * @param input is the user input, containing name of command,options and arguments
	 * @see Parser.getCommandArguments(String)
	 */
	@Override
	public List<String> getArguments(String input) {
		return parser.getCommandArguments(input);
	}

	/**
	 * Execute pipe command. 
	 * Gets the output from the previous command and makes it input for the next command.
	 * 
	 * @param arguments
	 * @see Parser.getCommandArguments(String)
	 */
	@Override
	public String executeCommand(List<String> arguments)
			throws ExistanceException, NotEnoughMemoryException, InvalidArgumentException, IOException {
		String[] currentCommandArguments;
		Command currentCommand;
		String result = "";
		String newResult = " ";

		for (int i = 1; i < arguments.size(); i++) {
			result = getResultFromSubCommand(arguments, i - 1);
			currentCommandArguments = parser.getArgumentsSplitedBySpace(arguments.get(i));
			currentCommand = commandFactory.getCommand(currentCommandArguments[0]);
			List<String> args = new ArrayList<String>(Arrays.asList(currentCommandArguments));
			args.remove(0);
			args.add(result);

			newResult = currentCommand.executeCommand(args);
		}

		return newResult;
	}

	private String getResultFromSubCommand(List<String> arguments, int index)
			throws ExistanceException, NotEnoughMemoryException, InvalidArgumentException, IOException {
		String[] currentCommandArguments;
		Command currentCommand;
		String result = "";

		currentCommandArguments = parser.getArgumentsSplitedBySpace(arguments.get(index));
		currentCommand = commandFactory.getCommand(currentCommandArguments[0]);
		List<String> args = new ArrayList<String>(Arrays.asList(currentCommandArguments));
		args.remove(0);
		result = currentCommand.executeCommand(args);

		return result;
	}
}
