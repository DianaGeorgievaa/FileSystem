package commands;

import java.io.IOException;
import java.util.List;

import exceptions.ExistanceException;
import exceptions.NotEnoughMemoryException;
import filesystem.FileSystem;
import filesystem.RealFileSystem;

public class CreateFileCommand extends Command {

	public CreateFileCommand(FileSystem fileSystem) {
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
	 * Executes the command, after checking for available memory.
	 *
	 * @param arguments contains options and arguments, without the name of the
	 *                  command.
	 * @see freeUpMemory()
	 */
	@Override
	public String executeCommand(List<String> arguments)
			throws ExistanceException, NotEnoughMemoryException, IOException {
		if (fileSystem.getClass() == RealFileSystem.class) {
			freeUpMemory();
		}
		Command.fileSystem.addFile(arguments);
		return "";
	}

	/**
	 * If there is no available memory, free up memory as deleting removed files.
	 */
	private void freeUpMemory() throws NotEnoughMemoryException {
		while (((RealFileSystem) fileSystem).getFiles().getAvailableMemory() < 0) {
			if (((RealFileSystem) fileSystem).getFiles().getRemovedFiles().size() == 0) {
				throw new NotEnoughMemoryException("Not enough memory!");
			}
			((RealFileSystem) fileSystem).getFiles().deleteRemovedFile();
		}
	}
}
