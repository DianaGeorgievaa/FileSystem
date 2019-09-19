package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

	/**
	 * Returns the name of command by the given input.
	 * 
	 * @param input is the user input, containing the name of the command,options
	 *              and arguments.
	 * @see splittedByScpace(String)
	 */
	public String getCommand(String input) {
		if (isCommandPipe(input)) {
			return "|";
		}
		String[] splittedBySpace = this.getArgumentsSplitedBySpace(input);
		String command = splittedBySpace[0];
		return command;
	}

	/**
	 * Returns the directory names from path.
	 * 
	 * @param path
	 * @see splittedByScpace(String)
	 */
	public String[] getArgumentsSplittedBySlash(String path) {
		String[] directories = path.split("/");
		return directories;
	}

	String[] getArgumentsSplitedBySpace(String arguments) {
		String[] splittedArguments = arguments.trim().split("\\s+");
		return splittedArguments;
	}

	List<String> getCommandArguments(String input) {
		String[] splittedArguments;
		boolean isPipe = false;
		if (isCommandPipe(input)) {
			splittedArguments = splitByPipe(input);
			isPipe = true;
		} else {
			splittedArguments = getArgumentsSplitedBySpace(input);
		}
		List<String> arguments = new ArrayList<String>(Arrays.asList(splittedArguments));
		if (!isPipe) {
			arguments.remove(0);
		}
		return arguments;
	}

	String getPath(String input) {
		String[] splittedBySpace = this.getArgumentsSplitedBySpace(input);
		String path = splittedBySpace[1];
		return path;
	}

	private boolean isCommandPipe(String input) {
		return input.contains("|");
	}

	private String[] splitByPipe(String input) {
		String[] splittedByPipe = input.trim().split("\\|");
		return splittedByPipe;
	}
}
