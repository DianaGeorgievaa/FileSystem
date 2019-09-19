package commands;

import java.util.List;

import exceptions.ExistanceException;
import filesystem.FileSystem;

public class PwdCommand extends Command {

	public PwdCommand(FileSystem fileSystem) {
		super(fileSystem);
		parser = new Parser();
	}

	/**
	 * Returns arguments for the command.
	 * 
	 * @param input is the user input, containing the name of the command,options
	 *              and arguments.
	 * @see Parser.getCommandArguments(String)
	 */
	@Override
	public List<String> getArguments(String input) {
		return parser.getCommandArguments(input);
	}

	/**
	 * Executes command.
	 * 
	 * @param arguments contains options and arguments, without the name of the
	 *                  command.
	 */
	@Override
	public String executeCommand(List<String> arguments) throws ExistanceException {
		return Command.fileSystem.getCurrentDirectoryPath();
	}
}
