package commands;

import java.util.List;

import exceptions.ExistanceException;
import filesystem.FileSystem;

public class LsCommand extends Command {

	public LsCommand(FileSystem fileSystem) {
		super(fileSystem);
		parser = new Parser();
	}

	/**
	 * Returns arguments for the command.
	 * 
	 * @param input is the user input, containing name of command,options and
	 *              arguments
	 * @see Parser.getCommandArguments(String)
	 */
	@Override
	public List<String> getArguments(String input) {
		return parser.getCommandArguments(input);
	}

	/**
	 * Executes command, after checking for option --sorted.
	 * 
	 * @param arguments contains options and arguments, without the name of the
	 *                  command.
	 * @see isSortedOption(List<String>)
	 */
	@Override
	public String executeCommand(List<String> arguments) throws ExistanceException {
		if (isSortedOption(arguments)) {
			return Command.fileSystem.listFilesSorted();
		}
		return Command.fileSystem.listFiles();
	}

	private boolean isSortedOption(List<String> arguments) {
		if (arguments.size() == 1) {
			if (arguments.get(0) == "--sorted") {
				return true;
			}
		}
		return false;
	}
}
