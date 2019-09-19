package commands;

import java.io.IOException;
import java.util.List;

import exceptions.ExistanceException;
import filesystem.FileSystem;

public class MkdirCommand extends Command {

	public MkdirCommand(FileSystem fileSystem) {
		super(fileSystem);
		parser = new Parser();
	}

	/**
	 * Returns arguments for the command.
	 * 
	 * @param is the user input, containing name of command,options and arguments
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
	public String executeCommand(List<String> arguments) throws ExistanceException, IOException {
		Command.fileSystem.makeDirectory(arguments);
		return "";

	}

}
