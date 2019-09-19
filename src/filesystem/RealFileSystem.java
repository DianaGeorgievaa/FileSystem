package filesystem;

import java.util.List;

import exceptions.InvalidArgumentException;
import exceptions.DirectoryAlreadyExistsException;
import exceptions.DirectoryDoesNotExistException;
import exceptions.FileAlreadyExistsException;
import exceptions.FileDoesNotExistException;
import files.Directory;
import files.Files;

public class RealFileSystem extends FileSystem {
	private String path;
	private Directory home;
	private Directory currentDirectory;
	private Files files;

	public RealFileSystem() {
		Directory root = new Directory();
		files = new Files();
		home = new Directory("home", root);
		currentDirectory = home;
		path="/";
	}

	public Directory getCurrentDirectory() {
		return currentDirectory;
	}

	@Override
	public void makeDirectory(List<String> arguments) throws DirectoryAlreadyExistsException {
		String directoryName = arguments.get(0);
		currentDirectory.makeDirectory(directoryName);
	}

	@Override
	public void changeDirectory(List<String> arguments) throws DirectoryDoesNotExistException {
		for (int i = 0; i < arguments.size(); i++) {
			if (arguments.get(i).equals("..")) {
				currentDirectory = currentDirectory.getParent();
			} else {
				String name = currentDirectory.getSubDirectoryIfExists(arguments.get(i)).getName();
				currentDirectory = currentDirectory.getSubDirectoryIfExists(arguments.get(i));
				path+=name;
				path+="/";
			}
		}
	}

	@Override
	public void addFile(List<String> arguments) throws FileAlreadyExistsException {
		for (int i = 0; i < arguments.size(); i++) {
			String fileName = arguments.get(i);
			currentDirectory.addFile(fileName);
			files.add(arguments.get(0));
		}
	}

	@Override
	public String readFileContent(List<String> arguments) throws FileDoesNotExistException {
		return currentDirectory.concatenateFiles(arguments);
	}

	@Override
	public void writeFileContent(List<String> arguments) throws FileDoesNotExistException, InvalidArgumentException {
		String fileName = arguments.get(0);
		boolean toOverwrite = Boolean.parseBoolean(arguments.get(1));
		String text = arguments.get(2);
		int line = Integer.parseInt(arguments.get(3));

		currentDirectory.writeToFile(fileName, toOverwrite, text, line);
		files.writeToFile(fileName, toOverwrite, text, line);
	}

	@Override
	public String listFiles() {
		return currentDirectory.getFileNamesInDirectory();
	}

	@Override
	public String listFilesSorted() {
		return currentDirectory.getFileNamesInDirectoryBySize();
	}

	@Override
	public String countWords(String name) throws FileDoesNotExistException {
		String numberOfWords = Long.toString(currentDirectory.getNumberOfWords(name));
		return numberOfWords;
	}

	@Override
	public String countLines(String name) throws FileDoesNotExistException {
		String numberOfLines = String.valueOf(currentDirectory.getNumberOfLines(name));
		return numberOfLines;
	}

	@Override
	public void removeFile(String name) throws FileDoesNotExistException {
		currentDirectory.removeFile(name);
		files.remove(name);
	}

	@Override
	public void removeContent(List<String> arguments) throws FileDoesNotExistException, InvalidArgumentException {
		String fileName = arguments.get(0);
		int firstLineNumber = Integer.parseInt(arguments.get(1));
		int secondLineNumber = Integer.parseInt(arguments.get(2));

		currentDirectory.deleteLinesFromFileContent(fileName, firstLineNumber, secondLineNumber);
		files.deleteLines(fileName, firstLineNumber, secondLineNumber);
	}

	@Override
	public String getCurrentDirectoryPath() {
		return path;
	}

	public Files getFiles() {
		return files;
	}
}