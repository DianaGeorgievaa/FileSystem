package commands;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import exceptions.ExistanceException;
import exceptions.FileDoesNotExistException;
import files.Directory;
import filesystem.FileSystem;
import filesystem.RealFileSystem;
import filesystem.VirtualFileSystem;

public class WcCommand extends Command {

	public WcCommand(FileSystem fileSystem) {
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
	 * Executes command, after checking for -l option.
	 * 
	 * @param input is the user input, containing name of command,options and
	 *              arguments
	 */
	@Override
	public String executeCommand(List<String> arguments) throws ExistanceException, FileNotFoundException, IOException {
		if (arguments.get(0).contentEquals("-l")) {
			return executeCountingLinesOption(arguments);
		}
		return executeCountingWordsOption(arguments);
	}

	private String executeCountingLinesOption(List<String> arguments)
			throws FileDoesNotExistException, FileNotFoundException, IOException {
		if (fileSystem.getClass() == VirtualFileSystem.class) {
			if (arguments.size() > 1) {
				return getNumberOfNewLines(arguments);
			}
			return Command.fileSystem.countLines(arguments.get(1));
		}
		return countLinesForRealFileSystem(arguments);
	}

	private String executeCountingWordsOption(List<String> arguments)
			throws FileDoesNotExistException, FileNotFoundException, IOException {
		if (fileSystem.getClass() == VirtualFileSystem.class) {
			if (arguments.size() != 0) {
				return Integer.toString(arguments.size());
			}
			return Command.fileSystem.countWords(arguments.get(0));
		}
		return countWordsForRealFileSystem(arguments);
	}

	private String countLinesForRealFileSystem(List<String> arguments)
			throws FileDoesNotExistException, FileNotFoundException, IOException {
		RealFileSystem realFileSystem = (RealFileSystem) Command.fileSystem;
		Directory currentDirectory = realFileSystem.getCurrentDirectory();
		if (currentDirectory.getFiles().isExistingFile(arguments.get(1))) {
			return Command.fileSystem.countLines(arguments.get(1));
		}
		return getNumberOfNewLines(arguments);
	}

	private String countWordsForRealFileSystem(List<String> arguments)
			throws FileDoesNotExistException, FileNotFoundException, IOException {
		RealFileSystem realFileSystem = (RealFileSystem) Command.fileSystem;
		Directory currentDirectory = realFileSystem.getCurrentDirectory();
		if (currentDirectory.getFiles().isExistingFile(arguments.get(0))) {
			return Command.fileSystem.countWords(arguments.get(0));
		}
		return Integer.toString(arguments.size());
	}

	private int getNumberOfLinesInText(List<String> arguments) {
		int countLines = 0;
		for (int i = 0; i < arguments.size(); i++) {
			if (arguments.get(i).equals("\\n")) {
				countLines++;
			}
		}
		return countLines;
	}

	private String getNumberOfNewLines(List<String> arguments) {
		long countLines = 0;
		for (int i = 0; i < arguments.size(); i++) {
			String str = arguments.get(i);
			countLines += str.chars().filter(x -> x == 10).count();
		}
		countLines += getNumberOfLinesInText(arguments);
		return Long.toString(countLines);
	}
}
