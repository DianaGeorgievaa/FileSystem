package commands;

import java.io.IOException;
import java.util.List;
import exceptions.FileDoesNotExistException;
import filesystem.FileSystem;

public class CatCommand extends Command {

	public CatCommand(FileSystem fileSystem) {
		super(fileSystem);
		parser = new Parser();
	}

	/**
	 * Returns the arguments for the command.
	 * 
	 * @param input is the user input, containing name of command,options and arguments
	 * @see Parser.getCommandArguments(String)
	 */
	@Override
	public List<String> getArguments(String input) {
		return parser.getCommandArguments(input);
	}

	/**
	 * Executes command.
	 * 
	 * @param arguments contains options and arguments, without the name of the command.
	 */
	@Override
	public String executeCommand(List<String> arguments) throws FileDoesNotExistException, IOException {
		return Command.fileSystem.readFileContent(arguments);
	}
}
