package commands;

import java.util.Arrays;
import java.util.List;

import exceptions.ExistanceException;
import filesystem.FileSystem;

public class CdCommand extends Command {

	public CdCommand(FileSystem fileSystem) {
		super(fileSystem);
		parser = new Parser();
	}

	/**
	 * From input-path returns directory names.
	 * 
	 * @param input is the user input, containing name of command,options and arguments
	 */
	@Override
	public List<String> getArguments(String input) {
		String[] splittedBySlash = parser.getArgumentsSplittedBySlash(parser.getPath(input));
		List<String> directoryNames = Arrays.asList(splittedBySlash);
		return directoryNames;
	}

	/**
	 * Executes command.
	 * 
	 * @param arguments contains options and arguments, without the name of the command.
	 */
	@Override
	public String executeCommand(List<String> arguments) throws ExistanceException {
		Command.fileSystem.changeDirectory(arguments);
		return "";
	}
}
