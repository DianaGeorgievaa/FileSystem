package filesystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import exceptions.DirectoryAlreadyExistsException;
import exceptions.DirectoryDoesNotExistException;
import exceptions.FileAlreadyExistsException;
import exceptions.FileDoesNotExistException;
import exceptions.InvalidArgumentException;
import files.Directory;
import files.Files;

public abstract class FileSystem {

	public abstract void makeDirectory(List<String> arguments) throws DirectoryAlreadyExistsException, IOException;

	public abstract void changeDirectory(List<String> arguments) throws DirectoryDoesNotExistException;

	public abstract void addFile(List<String> arguments) throws FileAlreadyExistsException, IOException;

	public abstract String readFileContent(List<String> arguments) throws FileDoesNotExistException, IOException;

	public abstract void writeFileContent(List<String> arguments)
			throws FileDoesNotExistException, InvalidArgumentException, IOException;

	public abstract String listFiles();

	public abstract String listFilesSorted();

	public abstract String countWords(String name) throws FileDoesNotExistException, FileNotFoundException, IOException;

	public abstract String countLines(String name) throws FileDoesNotExistException, FileNotFoundException, IOException;

	public abstract void removeFile(String name) throws FileDoesNotExistException, IOException;

	public abstract void removeContent(List<String> arguments)
			throws FileDoesNotExistException, InvalidArgumentException, IOException;

	public abstract String getCurrentDirectoryPath();
}
