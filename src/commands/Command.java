package commands;

import java.io.IOException;
import java.util.List;

import exceptions.ExistanceException;
import exceptions.InvalidArgumentException;
import exceptions.NotEnoughMemoryException;
import filesystem.FileSystem;

public abstract class Command {

	protected static FileSystem fileSystem;
	protected Parser parser;

	public Command(FileSystem fileSystem) {
			Command.fileSystem = fileSystem;
	}

	public static FileSystem getFileSystem() {
		return fileSystem;
	}

	public static void setFileSystem(FileSystem fileSystem) {
		 Command.fileSystem = fileSystem;
	}
	
	public abstract List<String> getArguments(String input);

	public abstract String executeCommand(List<String> arguments)
			throws ExistanceException, NotEnoughMemoryException, InvalidArgumentException, IOException;
}
